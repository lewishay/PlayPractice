package connectors

import controllers.ControllerBaseSpec
import play.api.libs.ws.WSClient

class BattleNetConnectorSpec extends ControllerBaseSpec {

  "BattleNetConnector" should {

    val connector = new BattleNetConnector(mock[WSClient], mockAppConfig)

    "generate the correct Boss url" in {
      connector.bossUrl("77") shouldBe s":///wow/boss/77?locale=en_GBS&apikey=${connector.apiKey}"
    }

    "generate the correct Zone url" in {
      connector.zoneUrl("77") shouldBe s":///wow/zone/77?locale=en_GB&apikey=${connector.apiKey}"
    }
  }
}
