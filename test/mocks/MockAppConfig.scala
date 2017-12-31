package mocks

import config.AppConfig
import play.api.Configuration

class MockAppConfig(val runModeConfiguration: Configuration) extends AppConfig {

  override val battleNetService: String = ""
}
