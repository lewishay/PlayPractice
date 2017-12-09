package connectors

import javax.inject.Inject

import play.api.libs.ws._

import scala.concurrent.{ExecutionContext, Future}

class BattleNetConnector @Inject()(ws: WSClient) {

  val apiKey: String = "9f88h2qzeu82vxxscpr54wuyy89cdzsa"

  def bossUrl(bossID: String): String = s"https://eu.api.battle.net/wow/boss/$bossID?locale=en_GBS&apikey=$apiKey"
  def zoneUrl(zoneID: String): String = s"https://eu.api.battle.net/wow/zone/$zoneID?locale=en_GB&apikey=$apiKey"

  def getBoss(bossID: Int)(implicit ec: ExecutionContext): Future[WSResponse] = {
    val request: WSRequest = ws.url(bossUrl(bossID.toString))
    request.get()
  }

  def getZone(zoneID: Int)(implicit ec: ExecutionContext): Future[WSResponse] = {
    val request: WSRequest = ws.url(zoneUrl(zoneID.toString))
    request.get()
  }
}
