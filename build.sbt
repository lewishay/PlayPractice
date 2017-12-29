import play.core.PlayVersion
import sbt.Tests.{Group, SubProcess}

val appName = "PlayPractice"
lazy val appDependencies: Seq[ModuleID] = compile ++ test()
lazy val plugins: Seq[Plugins] = Seq.empty
lazy val playSettings: Seq[Setting[_]] = Seq.empty
 
val compile: Seq[ModuleID] = Seq(
  ws,
  ehcache,
  guice
)

def test(scope: String = "test, it"): Seq[ModuleID] = Seq(
  "com.github.tomakehurst" % "wiremock" % "2.6.0" % "test",
  "com.typesafe.play" %% "play-test" % PlayVersion.current % "test",
  "org.jsoup" % "jsoup" % "1.10.3" % "test",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % "test"
)

def oneForkedJvmPerTest(tests: Seq[TestDefinition]): Seq[Group] = tests map {
  test =>
    Group(
      test.name,
      Seq(test),
      SubProcess(ForkOptions(runJVMOptions = Seq("-Dtest.name=" + test.name, "-Dlogger.resource=logback-test.xml")))
    )
}

lazy val coverageSettings: Seq[Setting[_]] = {
  import scoverage.ScoverageKeys

  val excludedPackages = Seq(
    "<empty>",
    "Reverse.*",
    "app.*",
    "router.*"
  )

  Seq(
    ScoverageKeys.coverageExcludedPackages := excludedPackages.mkString(";"),
    ScoverageKeys.coverageMinimum := 90,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true
  )
}

lazy val project: Project = Project(appName, file("."))
  .enablePlugins(Seq(PlayScala) ++ plugins: _*)
  .settings(playSettings: _*)
  .settings(coverageSettings: _*)
  .settings(
    scalaVersion := "2.11.11",
    libraryDependencies ++= appDependencies,
    retrieveManaged := true,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    routesGenerator := InjectedRoutesGenerator
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    Keys.fork in IntegrationTest := false,
    unmanagedSourceDirectories in IntegrationTest := (baseDirectory in IntegrationTest) (base => Seq(base / "it")).value,
    testGrouping in IntegrationTest := oneForkedJvmPerTest((definedTests in IntegrationTest).value),
    parallelExecution in IntegrationTest := false)
  .settings(resolvers ++= Seq(
    "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
    "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
  ))
  .settings(scalacOptions in ThisBuild ++= Seq("-feature"))
