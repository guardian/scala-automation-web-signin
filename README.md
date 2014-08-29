scala-automation-web-signin
-----------------------------
The purpose of this module is to login to the Guardian website through an API, rather than through the web interface. It is to be used in conjuction with test automation.

To use this just edit your build.sbt as follows:

libraryDependencies ++= Seq(
  "com.gu" %% "scala-automation-web-signin" % "1.xxx"
)

Then add the following to your scala test configuration (see the [Scala Test Automation Framework](https://github.com/guardian/scala-automation)):

```
"idApiRoot" : "https://idapi.code.dev-theguardian.com"
"loginEmail": "your guardian account login email"
"loginPassword": "your guardian account password"
```

Then simply use the ```com.gu.automation.support.LogIn``` class to login. By default it will use the above configuration parameters from the root but if you need, you can put them in an object as seen by the linked example below.

See the (currently commented out) example code in [LoggingInTest](src/test/scala/com/gu/automation/signin/LoggingInTest.scala)
