package controllers

import models.CommitLog
import play.api.http.Status
import play.api.test.Helpers._
import services.GithubService

class GithubControllerSpec extends ControllerBaseSpec {

  val service: GithubService = mock[GithubService]

  def setup(): Any = {
    for (_ <- 1 to 4) {
      (service.getCommits(_: String, _: String, _: String))
        .expects(*, *, *)
        .returns(Some(CommitLog("", "", List(("", "", "", "")))))
    }
  }

  def controller: GithubController = {
    setup()
    new GithubController(service)
  }


  "Calling the commitsPage action" should {

    val result = controller.commitsPage(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }
}
