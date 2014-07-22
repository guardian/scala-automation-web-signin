package com.gu.automation.signin

import com.gu.automation.core.WebDriverFeatureSpec
import org.scalatest.Matchers

class LoggingInTest extends WebDriverFeatureSpec with Matchers {

    info("Tests for the API Logging in function")

    feature("should be able to log in to the browser") {

      // could add another test with a fake AuthApi checking the cookies really are set

      /**
       * This is an end to end test that we really end up logged in.
       *
       * To pass it needs a local.conf containing something like
       *
       * "idApiRoot" : "https://idapi.code.dev-theguardian.com"
       * testBaseUrl: "http://m.code.dev-theguardian.com"
       * memberLogin: {
       *   "loginEmail" : "regidqa@gmail.com"
       *   "loginPassword" : "ask_gwyn!"
       * }
       * browser: chrome

       */
      // REMOVED because it can't run in the jenkins env, and we can't run sbt 'test-only -- -l needsBrowser' from the sbt-release task easily
//      scenarioWeb("check we are logged in when we have added the cookies", Tag("needsBrowser")) { implicit driver: WebDriver =>
//
//        LogIn("memberLogin")
//
//        // now go to a URL where we are probably logged in
//        driver.get(Config().getTestBaseUrl())
//        val userSpan = driver.findElement(By.xpath("//div[@data-component='identity-profile']")).findElement(By.className("js-profile-info"))
//        userSpan.getText should be ("Reg Idtester")
//      }
    }

}
