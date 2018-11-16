package config

import javax.inject.{Inject, Singleton}

import config.features.Features
import play.api.Configuration

trait AppConfig extends ServicesConfig {
  val protocol: String
  val battleNetService: String
  val githubService: String
  val features: Features
  val adminPasswordHash: String
}

@Singleton
class FrontendAppConfig @Inject()(val runModeConfiguration: Configuration) extends AppConfig {

  override val protocol: String = secureProtocol
  override val battleNetService: String = baseUrl("battle-net")
  override val githubService: String = baseUrl("github")
  override val features = new Features(runModeConfiguration)
  override val adminPasswordHash: String = "$pbkdf2-sha512$20000$$UlOhguhiYJONBjf9E2yr7kYCp.3CM0bq8AeHV/o5EL4"
}
