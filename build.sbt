scalaVersion := "2.10.0"

name := "play2-mail-plugin"

organization := "play.modules.mail"

version := "0.4.1"

resolvers ++= Seq(
    "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"
)

scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:reflectiveCalls"
)

libraryDependencies ++= Seq(
  "org.codemonkey.simplejavamail" % "simple-java-mail" % "2.1" exclude("log4j", "log4j"),
  "org.slf4j" % "log4j-over-slf4j" % "1.7.5",
  "com.typesafe.play" %% "play" % "2.2.2" % "provided",
  "com.typesafe.play" %% "play-test" % "2.2.2" % "test",
  "org.specs2" %% "specs2" % "2.3.4" % "test",
  "junit" % "junit" % "4.8" % "test"
)
