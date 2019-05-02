package config

import play.api.Configuration

trait ServicesConfig {

  protected def runModeConfiguration: Configuration

  def getString(configString: String): String = runModeConfiguration.get[String](configString)
}
