package services

import javax.inject.Inject

import connectors.BattleNetConnector
import models.{Boss, Zone}
import play.api.libs.json.Json

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class BattleNetService @Inject()(connector: BattleNetConnector) {

  def getBoss(bossID: Int)(implicit ec: ExecutionContext): Future[Boss] = {
    connector.getBoss(bossID).map { result =>
      val rawBoss = Json.parse(result.body)
      Boss(
        (rawBoss \ "name").get.toString,
        (rawBoss \ "description").get.toString,
        (rawBoss \ "health").get.toString,
        (rawBoss \ "level").get.toString,
        Await.result(getZone((rawBoss \ "zoneId").get.toString.toInt), Duration(5, "seconds"))
      )
    }
  }

  def getZone(zoneID: Int)(implicit ec: ExecutionContext): Future[Zone] = {
    connector.getZone(zoneID).map { result =>
      val rawZone = Json.parse(result.body)
      Zone(
        (rawZone \ "name").get.toString,
        (rawZone \ "description").get.toString
      )
    }
  }
}
