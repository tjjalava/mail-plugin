scalaVersion := "2.11.1"

name := "play2-mail-plugin"

organization := "play.modules.mail"

version := "0.6-SNAPSHOT"

crossScalaVersions := Seq("2.10.4", "2.11.1")

lazy val playVersion = "2.3.6"

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
  "org.codemonkey.simplejavamail" % "simple-java-mail" % "2.1",
  "com.typesafe.play" %% "play" % playVersion % "provided",
  "com.typesafe.play" %% "play-test" % playVersion % "test",
  "org.specs2" %% "specs2" % "2.3.13" % "test",
  "junit" % "junit" % "4.8" % "test"
)
