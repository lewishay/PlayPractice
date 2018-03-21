package connectors

import controllers.ControllerBaseSpec
import play.api.libs.ws.WSClient

class GithubConnectorSpec extends ControllerBaseSpec {

  "GithubConnector" should {

    val connector = new GithubConnector("myUser", "myRepo", "myBranch", mock[WSClient], mockAppConfig)

    "generate the correct url" in {
      connector.getUrl shouldBe ":///myUser/myRepo/commits/myBranch.atom"
    }
  }
}