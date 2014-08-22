scala-automation-web-signin
-----------------------------
To use this just edit your build.sbt as follows:

libraryDependencies ++= Seq(
  "com.gu" %% "scala-automation-web-signin" % "1.xxx"
)

Add something to your conf file e.g. for CODE
"idApiRoot" : "https://idapi.code.dev-theguardian.com"

See the (currently commented out) example code in [LoggingInTest](src/test/scala/com/gu/automation/signin/LoggingInTest.scala)
