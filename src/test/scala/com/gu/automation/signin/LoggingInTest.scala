package com.gu.automation.signin

import com.gu.automation.core.WebDriverFeatureSpec
import com.gu.automation.support.{LogIn, Config}
import org.openqa.selenium.{By, WebDriver}
import org.scalatest.Matchers

class LoggingInTest extends WebDriverFeatureSpec with Matchers {

    info("Tests for the API Logging in function")

    feature("should be able to log in to the browser") {

      scenario("check we can get the right cookie domains") { _ =>

        LogIn.getCookieDomain("http://www.theguardian.com/uk") should be (".theguardian.com")
        LogIn.getCookieDomain("http://localhost:9000/") should be ("localhost:9000")
        LogIn.getCookieDomain("https://www.theguardian.com/uk") should be (".theguardian.com")
        LogIn.getCookieDomain("https://m.code.dev-theguardian.com/") should be (".code.dev-theguardian.com")

      }

      /**
       * scenarioWeb handles starting and stopping the browser, you will have to declare an implicit driver as shown
       */
      scenarioWeb("check we are logged in when we have added the cookies") { implicit driver: WebDriver =>

        LogIn(Some("memberLogin"))
//       ExamplePage.goto()

        driver.get(Config().getTestBaseUrl())
        val userSpan = driver.findElement(By.xpath("//div[@data-component='identity-profile']")).findElement(By.className("js-profile-info"))
        userSpan.getText should be ("Reg Idtester")
      }
    }

}
