package connectors

import controllers.ControllerBaseSpec
import play.api.libs.ws.WSClient

class BattleNetConnectorSpec extends ControllerBaseSpec {

  "BattleNetConnector" should {

    "generate the correct url" in {

      val connector = new BattleNetConnector(mock[WSClient])
      connector.bossUrl("77") shouldEqual s"https://eu.api.battle.net/wow/boss/77?locale=en_GBS&apikey=${connector.apiKey}"
    }
  }
}
