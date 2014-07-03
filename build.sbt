import _root_.sbtrelease.ReleasePlugin._
import _root_.xerial.sbt.Sonatype._
import com.typesafe.sbt.SbtPgp._
import sbt.Keys._
import sbtrelease.ReleaseStateTransformations._
import sbtrelease._

name := "scala-automation-web-signin"

organization := "com.gu"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "Guardian GitHub Releases" at "http://guardian.github.io/maven/repo-releases",
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
)

libraryDependencies ++= Seq(
  //"org.scalatest" % "scalatest_2.10" % "2.0",
  //"com.typesafe.play" %% "play-ws" % "2.3.0"
  "com.gu" %% "scala-automation" % "1.22",
"com.gu" %% "scala-automation-api-client" % "1.0"
)

releaseSettings

sonatypeSettings

description := "let us add the guardian login cookies to webdriver"

scmInfo := Some(ScmInfo(
  url("https://github.com/guardian/scala-automation-web-signin"),
  "scm:git:git@github.com:guardian/scala-automation-web-signin.git"
))

pomExtra := (
  <url>https://github.com/guardian/scala-automation-web-signin</url>
    <developers>
      <developer>
        <id>johnduffell</id>
        <name>John Duffell</name>
        <url>https://github.com/johnduffell</url>
      </developer>
      <developer>
        <id>istvanpamer</id>
        <name>Istvan Pamer</name>
        <url>https://github.com/istvanpamer</url>
      </developer>
    </developers>
  )

licenses := Seq("Apache V2" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

ReleaseKeys.crossBuild := true

ReleaseKeys.releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,// new
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep( // instead of publishArtifacts
    action = state => Project.extract(state).runTask(PgpKeys.publishSigned, state)._1,
    enableCrossBuild = true
  ),
  setNextVersion,
  commitNextVersion,
  ReleaseStep(state => Project.extract(state).runTask(SonatypeKeys.sonatypeReleaseAll, state)._1),// new
  pushChanges
)
