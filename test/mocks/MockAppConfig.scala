package mocks

import config.AppConfig
import config.features.Features
import play.api.Configuration

class MockAppConfig(val runModeConfiguration: Configuration) extends AppConfig {

  override val protocol: String = ""
  override val battleNetService: String = ""
  override val githubService: String = ""
  override val features: Features = new Features(runModeConfiguration)
}
