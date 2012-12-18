name := "play2-mail-plugin"

organization := "play.modules.mail"

version := "0.2-SNAPSHOT"

resolvers += "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/"

resolvers += "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.codemonkey.simplejavamail" % "simple-java-mail" % "2.0",
  "play" %% "play" % "2.0.3" % "provided",
  "play" %% "play-test" % "2.0.3" % "test",
  "org.specs2" %% "specs2" % "1.7.1" % "test",
)
