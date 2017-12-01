import play.core.PlayVersion

name := "PlayPractice"
 
version := "1.0" 

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  ws,
  guice,
  "org.jsoup" % "jsoup" % "1.10.3" % "test",
  "com.typesafe.play" %% "play-test" % PlayVersion.current % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % "test"
)

// This stops the "re-run with feature" warnings
scalacOptions in ThisBuild ++= Seq("-feature")

lazy val `playpractice` = (project in file("."))
  .enablePlugins(PlayScala)
