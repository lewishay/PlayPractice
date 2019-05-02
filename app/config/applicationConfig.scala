package config

import javax.inject.{Inject, Singleton}

import config.features.Features
import play.api.Configuration

trait AppConfig extends ServicesConfig {
  val protocol: String
  val battleNetService: String
  val battleNetAccessToken: String
  val githubService: String
  val features: Features
  val adminPasswordHash: String
}

@Singleton
class FrontendAppConfig @Inject()(val runModeConfiguration: Configuration) extends AppConfig {

  override val protocol: String = getString("protocol")
  override val battleNetService: String = getString("services.battle-net.url")
  override val battleNetAccessToken: String = getString("services.battle-net.access-token")
  override val githubService: String = getString("services.github.url")
  override val features = new Features(runModeConfiguration)
  override val adminPasswordHash: String = "$pbkdf2-sha512$20000$$UlOhguhiYJONBjf9E2yr7kYCp.3CM0bq8AeHV/o5EL4"
}
