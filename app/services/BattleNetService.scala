package services

import javax.inject.Inject

import connectors.BattleNetConnector
import models.{Boss, Zone}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class BattleNetService @Inject()(connector: BattleNetConnector) {

  def getBoss(bossID: Int)(implicit ec: ExecutionContext): Future[Boss] = {
    connector.getBoss(bossID).map { result =>
      val rawBoss: JsValue = Json.parse(result.body)
      val description: String = (rawBoss \ "description").asOpt[String] match {
        case Some(x) => x
        case _ => "Not found!"
      }
      Boss(
        (rawBoss \ "name").as[String],
        description,
        (rawBoss \ "health").as[Int],
        (rawBoss \ "level").as[Int],
        Await.result(getZone((rawBoss \ "zoneId").as[Int]), Duration(10, "seconds"))
      )
    }
  }

  def getZone(zoneID: Int)(implicit ec: ExecutionContext): Future[Zone] = {
    connector.getZone(zoneID).map { result =>
      val rawZone = Json.parse(result.body)
      Zone(
        (rawZone \ "name").as[String],
        (rawZone \ "location" \ "name").as[String]
      )
    }
  }
}
