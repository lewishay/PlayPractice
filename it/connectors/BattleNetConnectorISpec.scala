package connectors

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import common.Common
import helpers.IntegrationBaseSpec
import stubs.BattleNetStub

import scala.concurrent.ExecutionContext

class BattleNetConnectorISpec extends IntegrationBaseSpec {

  implicit val ec: ExecutionContext = ExecutionContext.global
  val connector: BattleNetConnector = app.injector.instanceOf[BattleNetConnector]

  "Calling getBoss with a valid Boss ID" should {

    "return a Boss" in {
      def setupStubs(): StubMapping = BattleNetStub.successfulBoss
      setupStubs()
      val result: String = await(connector.getBoss(77))
      result shouldBe Common.exampleBoss.toString
    }
  }

  "Calling getBoss with an invalid Boss ID" should {

    "return an exception" in {
      def setupStubs(): StubMapping = BattleNetStub.failureBoss
      setupStubs()
      val result: String = await(connector.getBoss(88))
      result shouldBe "Request failed!"
    }
  }

  "Calling getZone with a valid Zone ID" should {

    "return a Zone" in {
      def setupStubs(): StubMapping = BattleNetStub.successfulZone
      setupStubs()
      val result: String = await(connector.getZone(77))
      result shouldBe Common.exampleZone.toString
    }
  }

  "Calling getZone with an invalid Zone ID" should {

    "return an exception" in {
      def setupStubs(): StubMapping = BattleNetStub.failureZone
      setupStubs()
      val result: String = await(connector.getZone(88))
      result shouldBe "Request failed!"
    }
  }
}
