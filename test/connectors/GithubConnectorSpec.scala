package connectors

import controllers.ControllerBaseSpec

class GithubConnectorSpec extends ControllerBaseSpec {

  "GithubConnector" should {

    val connector = new GithubConnector(mockAppConfig)

    "generate the correct url" in {
      connector.getUrl("myUser", "myRepo", "myBranch") shouldBe ":///myUser/myRepo/commits/myBranch.atom"
    }
  }
}