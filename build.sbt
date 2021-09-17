import play.core.PlayVersion
import sbt.Tests.{Group, SubProcess}

val appName = "PlayPractice"
lazy val appDependencies: Seq[ModuleID] = compile ++ test()
lazy val plugins: Seq[Plugins] = Seq.empty
lazy val playSettings: Seq[Setting[_]] = Seq.empty
 
val compile: Seq[ModuleID] = Seq(
  ws,
  ehcache,
  guice,
  "io.github.nremond" %% "pbkdf2-scala" % "0.6.3"
)

def test(scope: String = "test, it"): Seq[ModuleID] = Seq(
  "com.github.tomakehurst" % "wiremock-jre8" % "2.26.3" % scope,
  "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
  "org.jsoup" % "jsoup" % "1.10.3" % scope,
  "org.scalatest" %% "scalatest" % "3.1.4" % scope,
  "org.scalamock" %% "scalamock" % "4.1.0" % scope,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % scope,
  "com.vladsch.flexmark" % "flexmark-all" % "0.36.8" % scope
)

def oneForkedJvmPerTest(tests: Seq[TestDefinition]): Seq[Group] = tests map {
  test =>
    Group(
      test.name,
      Seq(test),
      SubProcess(ForkOptions().withRunJVMOptions(Vector("-Dtest.name=" + test.name, "-Dlogger.resource=logback-test.xml")))
    )
}

lazy val coverageSettings: Seq[Setting[_]] = {
  import scoverage.ScoverageKeys

  val excludedPackages = Seq(
    "<empty>",
    "app.*",
    "Reverse.*",
    "router.*",
    "views.html.*"
  )

  Seq(
    ScoverageKeys.coverageExcludedPackages := excludedPackages.mkString(";"),
    ScoverageKeys.coverageMinimumStmtTotal := 95,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true
  )
}

lazy val project: Project = Project(appName, file("."))
  .enablePlugins(Seq(PlayScala) ++ plugins: _*)
  .settings(playSettings: _*)
  .settings(coverageSettings: _*)
  .settings(
    PlayKeys.playDefaultPort := 9000,
    scalaVersion := "2.12.14",
    libraryDependencies ++= appDependencies,
    retrieveManaged := true,
    routesGenerator := InjectedRoutesGenerator,
    ThisBuild / scalacOptions ++= Seq("-feature"),
    resolvers ++= Seq(
      "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
      "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
    )
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    IntegrationTest / Keys.fork := false,
    IntegrationTest / unmanagedSourceDirectories := (IntegrationTest / baseDirectory) (base => Seq(base / "it")).value,
    IntegrationTest / testGrouping := oneForkedJvmPerTest((IntegrationTest / definedTests).value),
    IntegrationTest / parallelExecution := false
  )
