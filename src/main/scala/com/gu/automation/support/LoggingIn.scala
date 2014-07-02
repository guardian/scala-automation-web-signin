package com.gu.automation.support

import com.gu.automation.api.AuthApi

import scala.concurrent.Await
;

/**
 * Created by jduffell on 20/06/2014.
 */
trait LoggingIn {

  def addGULoginCookies(user: Option[String])(implicit driver: WebDriver) = {
    val future = AuthApi(Config().getIdApiRoot()).authenticate(Config().getLoginEmail(user), Config().getLoginPassword(user))

    val accessToken = Await.result(future, 30.seconds)
    val cookies = accessToken match { case Right(cookies) => cookies }
    cookies.foreach {
      case (key, value) =>
        val isSecure = key.startsWith("SC_")
        val cookie = new Cookie(key, value, null, "/", null, isSecure, isSecure)
        driver.manage().addCookie(cookie)
    }
  }

  def logInToGUPage[P](goto: () => P, user: Option[String] = None)(implicit driver: WebDriver): P = {
    goto()
    addGULoginCookies(user)
    goto()
  }

}
