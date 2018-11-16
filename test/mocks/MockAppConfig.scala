package mocks

import config.AppConfig
import config.features.Features
import play.api.Configuration

class MockAppConfig(val runModeConfiguration: Configuration) extends AppConfig {

  override val protocol: String = ""
  override val battleNetService: String = ""
  override val githubService: String = ""
  override val features: Features = new Features(runModeConfiguration)
  override val adminPasswordHash: String =
    "$pbkdf2-sha512$20000$$X6Ekl0uiSEXgb0t/JNztYi5V6JJCfnTKXZ5aXHRaWgI" // "testPass"
}
