package services

import javax.inject.Inject

import connectors.BattleNetConnector
import models.{Boss, ErrorModel, Zone}
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

class BattleNetService @Inject()(connector: BattleNetConnector) {

  def getBoss(bossID: Int)(implicit ec: ExecutionContext): Future[Either[ErrorModel, Boss]] = {
    connector.getBoss(bossID).flatMap {
      case Right(result) =>
        val rawBoss: JsValue = Json.parse(result)
        val description: String = (rawBoss \ "description").asOpt[String] match {
          case Some(x) => x
          case _ => "Not found!"
        }
        getZone((rawBoss \ "zoneId").as[Int]).map {
          case Right(zone) =>
            Right(Boss(
              (rawBoss \ "name").as[String],
              description,
              (rawBoss \ "health").as[Int],
              (rawBoss \ "level").as[Int],
              zone
            ))
          case Left(error) => Left(error)
        }

      case Left(error) => Future.successful(Left(error))
    }
  }

  def getZone(zoneID: Int)(implicit ec: ExecutionContext): Future[Either[ErrorModel, Zone]] = {
    connector.getZone(zoneID).map {
      case Right(result) =>
        val rawZone = Json.parse(result)
        Right(Zone(
          (rawZone \ "name").as[String],
          (rawZone \ "location" \ "name").as[String]
        ))
      case Left(error) => Left(error)
    }
  }
}
