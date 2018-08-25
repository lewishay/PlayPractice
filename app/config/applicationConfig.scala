package config

import javax.inject.{Inject, Singleton}

import play.api.Configuration

trait AppConfig extends ServicesConfig {
  val protocol: String
  val battleNetService: String
  val githubService: String
}

@Singleton
class FrontendAppConfig @Inject()(val runModeConfiguration: Configuration) extends AppConfig {

  override val protocol: String = secureProtocol
  override val battleNetService: String = baseUrl("battle-net")
  override val githubService: String = baseUrl("github")
}
