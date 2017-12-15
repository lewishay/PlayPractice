package services

import common.Common
import connectors.BattleNetConnector
import controllers.ControllerBaseSpec
import models.Boss

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

class BattleNetServiceSpec extends ControllerBaseSpec {

  private trait Test {
    val mockConnector: BattleNetConnector = mock[BattleNetConnector]
    val response: String

    lazy val service: BattleNetService = {
      (mockConnector.getBoss(_: Int))
        .expects(77)
        .returns(Future.successful(response))

      new BattleNetService(mockConnector)
    }
  }

  "Calling getBoss()" when {

    "a boss is found" should {

      "return a boss" in new Test {
        override val response: String = Common.exampleBoss.toString
        val result: Future[Boss] = service.getBoss(77)
        result shouldBe Some(Common.exampleBoss)
      }
    }

    "a boss is not found" should {

      "return ?" in new Test {
        override val response: String = "Request failed!"
        val result: Future[Boss] = service.getBoss(88)
        result shouldBe "Request failed!"
      }
    }
  }
}
