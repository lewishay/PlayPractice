package connectors

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import common.Common
import helpers.IntegrationBaseSpec
import stubs.BattleNetStub

import scala.concurrent.ExecutionContext

class BattleNetConnectorISpec extends IntegrationBaseSpec {

  private trait Test {
    def setupStubs(): StubMapping
    implicit val ec: ExecutionContext = ExecutionContext.global
    val connector: BattleNetConnector = app.injector.instanceOf[BattleNetConnector]
  }

  "Calling getBoss with a valid Boss ID" should {

    "return a Boss" in new Test {
      override def setupStubs(): StubMapping = BattleNetStub.successfulBoss
      val expected: String = Common.exampleBoss.toString
      setupStubs()
      val result: String = await(connector.getBoss(77))
      result shouldBe expected
    }
  }

  "Calling getBoss with an invalid Boss ID" should {

    "return a failure message" in new Test {
      override def setupStubs(): StubMapping = BattleNetStub.failureBoss
      val expected: String = "Request failed!"
      setupStubs()
      val result: String = await(connector.getBoss(88))
      result shouldBe expected
    }
  }
}
