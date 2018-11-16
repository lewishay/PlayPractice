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
  "com.github.tomakehurst" % "wiremock" % "2.6.0" % scope,
  "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
  "org.jsoup" % "jsoup" % "1.10.3" % scope,
  "org.scalatest" %% "scalatest" % "3.0.4" % scope,
  "org.scalamock" %% "scalamock" % "4.1.0" % scope,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % scope
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
    ScoverageKeys.coverageMinimum := 95,
    ScoverageKeys.coverageFailOnMinimum := false,
    ScoverageKeys.coverageHighlighting := true
  )
}

val jettyVersion = "9.2.13.v20150730"
val jettyDependencies = Set(
  "org.eclipse.jetty" % "jetty-server" % jettyVersion,
  "org.eclipse.jetty" % "jetty-servlet" % jettyVersion,
  "org.eclipse.jetty" % "jetty-security" % jettyVersion,
  "org.eclipse.jetty" % "jetty-servlets" % jettyVersion,
  "org.eclipse.jetty" % "jetty-continuation" % jettyVersion,
  "org.eclipse.jetty" % "jetty-webapp" % jettyVersion,
  "org.eclipse.jetty" % "jetty-xml" % jettyVersion,
  "org.eclipse.jetty" % "jetty-client" % jettyVersion,
  "org.eclipse.jetty" % "jetty-http" % jettyVersion,
  "org.eclipse.jetty" % "jetty-io" % jettyVersion,
  "org.eclipse.jetty" % "jetty-util" % jettyVersion,
  "org.eclipse.jetty.websocket" % "websocket-api" % jettyVersion,
  "org.eclipse.jetty.websocket" % "websocket-common" % jettyVersion,
  "org.eclipse.jetty.websocket" % "websocket-client" % jettyVersion
)

lazy val project: Project = Project(appName, file("."))
  .enablePlugins(Seq(PlayScala) ++ plugins: _*)
  .settings(playSettings: _*)
  .settings(coverageSettings: _*)
  .settings(
    PlayKeys.playDefaultPort := 9000,
    scalaVersion := "2.11.11",
    libraryDependencies ++= appDependencies,
    dependencyOverrides ++= jettyDependencies,
    retrieveManaged := true,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    routesGenerator := InjectedRoutesGenerator,
    scalacOptions in ThisBuild ++= Seq("-feature"),
    resolvers ++= Seq(
      "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
      "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
    )
  )
  .configs(IntegrationTest)
  .settings(inConfig(IntegrationTest)(Defaults.itSettings): _*)
  .settings(
    Keys.fork in IntegrationTest := false,
    unmanagedSourceDirectories in IntegrationTest := (baseDirectory in IntegrationTest) (base => Seq(base / "it")).value,
    testGrouping in IntegrationTest := oneForkedJvmPerTest((definedTests in IntegrationTest).value),
    parallelExecution in IntegrationTest := false
  )
