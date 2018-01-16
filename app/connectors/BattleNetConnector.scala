package connectors

import javax.inject.Inject

import config.AppConfig
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}

class BattleNetConnector @Inject()(ws: WSClient, appConfig: AppConfig) {

  val apiKey: String = "9f88h2qzeu82vxxscpr54wuyy89cdzsa"

  private def bossUrl(bossID: String): String = s"${appConfig.battleNetService}/wow/boss/$bossID?locale=en_GBS&apikey=$apiKey"
  private def zoneUrl(zoneID: String): String = s"${appConfig.battleNetService}/wow/zone/$zoneID?locale=en_GB&apikey=$apiKey"

  def getBoss(bossID: Int)(implicit ec: ExecutionContext): Future[String] = {
    val request: WSRequest = ws.url(bossUrl(bossID.toString))
    request.get().map {
      case response if response.status == 200 => response.body
      case response if response.status == 403 => "Request failed!"
    }.recoverWith {
      case _ => Future.failed(new Exception("Request failed!"))
    }
  }

  def getZone(zoneID: Int)(implicit ec: ExecutionContext): Future[String] = {
    val request: WSRequest = ws.url(zoneUrl(zoneID.toString))
    request.get().map {
      case response if response.status == 200 => response.body
      case response if response.status == 403 => "Request failed!"
    }.recoverWith {
      case _ => Future.failed(new Exception("Request failed!"))
    }
  }
}
