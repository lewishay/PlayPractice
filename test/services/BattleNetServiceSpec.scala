package services

import common.Common
import connectors.BattleNetConnector
import controllers.ControllerBaseSpec
import models.{Boss, Zone}

import scala.concurrent.{ExecutionContext, Future}

class BattleNetServiceSpec extends ControllerBaseSpec {

  val mockConnector: BattleNetConnector = mock[BattleNetConnector]
  val service: BattleNetService = new BattleNetService(mockConnector)

  "Calling getBoss()" when {

    "the connector returns a successful response body" should {

      val bossReturnString: String =
        """{
          |"name":"Fuck",
          |"description":"Fucking hell",
          |"health":999,
          |"level":9,
          |"zoneId":9
          |}"""
          .stripMargin.replace("\n", "")

      val zoneReturnString: String =
        """{
          |"name":"Fuck",
          |"location":{"name":"Fuckland"}
          |}"""
          .stripMargin.replace("\n", "")

      val successBoss: Boss = Boss("Fuck", "Fucking hell", 999, 9, Zone("Fuck", "Fuckland"))

      "return a boss" in {
        (mockConnector.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(bossReturnString))

        (mockConnector.getZone(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(zoneReturnString))

        val result: Boss = await(service.getBoss(1234))
        result shouldBe successBoss
      }
    }

    "the connector returns an unsuccessful response body" should {

      "return the empty default boss" in {
        (mockConnector.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful("Request failed!"))

        val result: Boss = await(service.getBoss(1234))
        result shouldBe Common.exampleBoss
      }
    }

    "the connector fails to return a response" should {

      "return an exception" in {
        val requestFailed = new Exception("Request failed!")

        (mockConnector.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.failed(requestFailed))

        intercept[Exception](await(service.getBoss(1234)))
      }
    }
  }
}
