package connectors

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import common.Common
import helpers.IntegrationBaseSpec
import models.ErrorModel
import play.api.http.Status
import stubs.BattleNetStub

import scala.concurrent.ExecutionContext

class BattleNetConnectorISpec extends IntegrationBaseSpec {

  implicit val ec: ExecutionContext = ExecutionContext.global
  val connector: BattleNetConnector = injector.instanceOf[BattleNetConnector]

  "Calling getBoss with a valid Boss ID" should {

    "return a Boss" in {
      def setupStubs(): StubMapping = BattleNetStub.successfulBoss
      setupStubs()
      val result: Either[ErrorModel, String] = await(connector.getBoss(77))
      result shouldBe Right(Common.exampleBoss.toString)
    }
  }

  "Calling getBoss with an invalid Boss ID" should {

    "return an ErrorModel" in {
      def setupStubs(): StubMapping = BattleNetStub.failureBoss
      setupStubs()
      val result: Either[ErrorModel, String] = await(connector.getBoss(88))
      result shouldBe Left(ErrorModel(Status.NOT_FOUND, BattleNetStub.failureBody))
    }
  }

  "Calling getZone with a valid Zone ID" should {

    "return a Zone" in {
      def setupStubs(): StubMapping = BattleNetStub.successfulZone
      setupStubs()
      val result: Either[ErrorModel, String] = await(connector.getZone(77))
      result shouldBe Right(Common.exampleZone.toString)
    }
  }

  "Calling getZone with an invalid Zone ID" should {

    "return an ErrorModel" in {
      def setupStubs(): StubMapping = BattleNetStub.failureZone
      setupStubs()
      val result: Either[ErrorModel, String] = await(connector.getZone(88))
      result shouldBe Left(ErrorModel(Status.NOT_FOUND, BattleNetStub.failureBody))
    }
  }
}
