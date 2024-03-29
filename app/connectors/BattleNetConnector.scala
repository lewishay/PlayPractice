package connectors

import javax.inject.Inject
import config.AppConfig
import models.ErrorModel
import play.api.Logging
import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}

class BattleNetConnector @Inject()(ws: WSClient, appConfig: AppConfig) extends Logging {

  val apiKey: String = "9f88h2qzeu82vxxscpr54wuyy89cdzsa"

  private[connectors] def bossUrl(bossID: String): String =
    s"${appConfig.protocol}://${appConfig.battleNetService}/wow/boss/$bossID?locale=en_GBS&apikey=$apiKey"
  private[connectors] def zoneUrl(zoneID: String): String =
    s"${appConfig.protocol}://${appConfig.battleNetService}/wow/zone/$zoneID?locale=en_GB&apikey=$apiKey"

  def getBoss(bossID: Int)(implicit ec: ExecutionContext): Future[Either[ErrorModel, String]] = {
    val request: WSRequest = ws.url(bossUrl(bossID.toString))
    request.get().map {
      case response if response.status == 200 => Right(response.body)
      case response =>
        logger.warn(s"[BattleNetConnector][getBoss] - API returned error. " +
          s"Status: ${response.status}, Body: ${response.body}")
        Left(ErrorModel(response.status, response.body))
    }
  }

  def getZone(zoneID: Int)(implicit ec: ExecutionContext): Future[Either[ErrorModel, String]] = {
    val request: WSRequest = ws.url(zoneUrl(zoneID.toString))
    request.get().map {
      case response if response.status == 200 => Right(response.body)
      case response =>
        logger.warn(s"[BattleNetConnector][getZone] - API returned error. " +
          s"Status: ${response.status}, Body: ${response.body}")
        Left(ErrorModel(response.status, response.body))
    }
  }
}
