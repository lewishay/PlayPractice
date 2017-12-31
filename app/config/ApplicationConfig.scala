package config

import javax.inject.{Inject, Singleton}

import play.api.Configuration

trait AppConfig extends ServicesConfig {
  val battleNetService: String
}

@Singleton
class FrontendAppConfig @Inject()(val runModeConfiguration: Configuration) extends AppConfig {

  override val battleNetService: String = s"http://${baseUrl("battle-net")}"
}
