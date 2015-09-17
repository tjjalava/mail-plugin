scalaVersion := "2.11.7"

name := "play2-mail-plugin"

organization := "play.modules.mail"

version := "2.4.0-SNAPSHOT"

lazy val playVersion = "2.4.3"

resolvers ++= Seq(
    "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:reflectiveCalls"
)

libraryDependencies ++= Seq(
  "org.codemonkey.simplejavamail" % "simple-java-mail" % "2.1" exclude("log4j", "log4j"),
  "org.slf4j" % "log4j-over-slf4j" % "1.7.5",
  "com.typesafe.play" %% "play" % playVersion % "provided",
  "com.typesafe.play" %% "play-test" % playVersion % "test",
  "org.specs2" %% "specs2-core" % "3.6.4" % "test"
)
