package connectors

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import common.Common
import helpers.IntegrationBaseSpec
import stubs.BattleNetStub

import scala.concurrent.ExecutionContext

class BattleNetConnectorISpec extends IntegrationBaseSpec {

  implicit val ec: ExecutionContext = ExecutionContext.global
  val connector: BattleNetConnector = app.injector.instanceOf[BattleNetConnector]

  "Calling getBoss with a valid URL" should {

    "return a Boss" in {
      def setupStubs(): StubMapping = BattleNetStub.successfulBoss
      setupStubs()
      val result: String = await(connector.getBoss(77))
      result shouldBe Common.exampleBoss.toString
    }
  }

  "Calling getBoss with an invalid URL" should {

    "return an exception" in {
      intercept[Exception](await(connector.getBoss(77, overrideUrl = true)))
    }
  }

  "Calling getZone with a valid URL" should {

    "return a Zone" in {
      def setupStubs(): StubMapping = BattleNetStub.successfulZone
      setupStubs()
      val result: String = await(connector.getZone(77))
      result shouldBe Common.exampleZone.toString
    }
  }

  "Calling getZone with an invalid URL" should {

    "return an exception" in {
      intercept[Exception](await(connector.getZone(77, overrideUrl = true)))
    }
  }
}
