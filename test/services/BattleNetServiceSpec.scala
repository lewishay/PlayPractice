package services

import common.Common
import connectors.BattleNetConnector
import controllers.ControllerBaseSpec
import models.Boss

import scala.concurrent.{ExecutionContext, Future}

class BattleNetServiceSpec extends ControllerBaseSpec {

  private trait Test {
    val mockConnector: BattleNetConnector = mock[BattleNetConnector]
    implicit val ec: ExecutionContext = ExecutionContext.global
    val requestFailed = new Exception("Request failed!")
    val successfulRequest: Boolean

    val bossReturnString: String =
      """{
        |"name":"Example",
        |"description":"Example",
        |"health":0,
        |"level":0,
        |"zoneId":0
        |}"""
        .stripMargin.replace("\n", "")

    val zoneReturnString: String =
      """{
        |"name":"Example",
        |"location":{"name":"Example"}
        |}"""
        .stripMargin.replace("\n", "")

    def setup(): Any = {
      if(successfulRequest) {
        (mockConnector.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(bossReturnString))

        (mockConnector.getZone(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.successful(zoneReturnString))
      } else {
        (mockConnector.getBoss(_: Int)(_: ExecutionContext))
          .expects(*, *)
          .returns(Future.failed(requestFailed))
      }
    }

    lazy val service: BattleNetService = {
      setup()
      new BattleNetService(mockConnector)
    }
  }

  "Calling getBoss()" when {

    "a request is successful" should {

      "return a boss" in new Test {
        override val successfulRequest = true
        val result: Boss = await(service.getBoss(1234))
        result shouldBe Common.exampleBoss
      }
    }

    "a request is not successful" should {

      "return an exception" in new Test {
        override val successfulRequest = false
        intercept[Exception](await(service.getBoss(1234)))
      }
    }
  }
}
