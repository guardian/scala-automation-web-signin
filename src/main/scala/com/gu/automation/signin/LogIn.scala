package com.gu.automation.support

import com.gu.automation.api.AuthApi
import org.openqa.selenium.{Cookie, WebDriver}
import scala.concurrent.duration._

import scala.concurrent.Await
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
object LogIn {

  def getCookieDomain(url: String) =
    """http(s?)://([^.]*(\.))?([^/]+).*$""".r.replaceAllIn(url, "$3$4")

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
    val baseUrl = Config().getTestBaseUrl()
    val loginDomain = getCookieDomain(baseUrl)

    driver.get(baseUrl) // have to be on the right url to add the cookies

    val future = AuthApi(idApiRoot).authenticate(loginEmail, loginPassword)

    val accessToken = Await.result(future, 10.seconds)
    val cookies = accessToken match {
      case Right(cookies) => cookies
      case Left(error) => throw new RuntimeException(s"authenticate $loginEmail failed: $error")
    }
    cookies.foreach {
      case (key, value) =>
        val isSecure = key.startsWith("SC_")
        val cookie = new Cookie(key, value, loginDomain, "/", null, isSecure, isSecure)
        driver.manage().addCookie(cookie)
    }
  }

}
