package com.gu.automation.support

import com.gu.automation.api.AuthApi
import org.openqa.selenium.WebDriver

import scala.concurrent.Await
import scala.concurrent.duration._
;

/**
 * Example: if your local.conf contains:
 *
 * userName: {
 *   loginEmail: "asdf@theguardian.com"
 *   loginPassword: passw0rd
 * }
 *
 * You would do this code:
 *
 * LogIn(Some("userName"))
 * ExamplePage.goto()
 *
 */
object LogIn extends CookieManager {

  def apply()(implicit driver: WebDriver) = {
    logIn(None, driver)
  }

  def apply(user: String)(implicit driver: WebDriver) = {
    logIn(Some(user), driver)
  }

  private def logIn(user: Option[String], driver: WebDriver) = {
    val idApiRoot = Config().getIdApiRoot()
    val loginEmail = Config().getLoginEmail(user)
    val loginPassword = Config().getLoginPassword(user)
    val future = AuthApi(idApiRoot).authenticate(loginEmail, loginPassword)
    val accessToken = Await.result(future, 10.seconds)
    val cookies = accessToken match {
      case Right(cookies) => cookies
      case Left(error) => throw new RuntimeException(s"authenticate $loginEmail failed: $error")
    }

    addCookies(driver, cookies)

  }

}
