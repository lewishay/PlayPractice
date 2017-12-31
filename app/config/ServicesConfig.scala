package config

import play.api.Configuration

trait ServicesConfig {

  protected def runModeConfiguration: Configuration
  protected lazy val rootServices = "services"

//  def baseUrl(serviceName: String): String = {
//    val host = getConfString(s"$serviceName.host", throw new RuntimeException(s"Could not find config $serviceName.host"))
//    val port = getConfInt(s"$serviceName.port", throw new RuntimeException(s"Could not find config $serviceName.port"))
//    s"$host:$port"
//  }

  def baseUrl(serviceName: String): String = {
    val url = getConfString(s"$serviceName.url", throw new RuntimeException(s"Could not find config $serviceName.url"))
    url
  }

  def getConfString(confKey: String, defString: => String): String = {
    runModeConfiguration.get[String](s"$rootServices.$confKey")
  }

  def getConfInt(confKey: String, defInt: => Int): Int = {
    runModeConfiguration.get[Int](s"$rootServices.$confKey")
  }
}
