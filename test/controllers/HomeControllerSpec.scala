package controllers

import play.api.http.Status
import play.api.mvc.Result
import play.api.test.Helpers._

import scala.concurrent.Future

class HomeControllerSpec extends ControllerBaseSpec {

  "Calling the index action" should {

    val result: Future[Result] = new HomeController(cc).home(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }
}
