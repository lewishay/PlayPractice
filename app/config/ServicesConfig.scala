package config

import play.api.Configuration

trait ServicesConfig {

  protected def runModeConfiguration: Configuration
  protected lazy val rootServices = "services"

  def baseUrl(serviceName: String): String = {
    getConfString(s"$serviceName.url", throw new RuntimeException(s"Could not find config $serviceName.url"))
  }

  def secureProtocol: String = {
    getConfString(s"protocol", throw new RuntimeException(s"Could not find config protocol"))
  }

  private def getConfString(confKey: String, defString: => String): String = {
    runModeConfiguration.get[String](s"$rootServices.$confKey")
  }
}
