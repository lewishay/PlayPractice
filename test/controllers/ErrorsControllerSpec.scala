package controllers

import play.api.http.Status
import play.api.mvc.Result
import play.api.test.Helpers._

import scala.concurrent.Future

class ErrorsControllerSpec extends ControllerBaseSpec {

  "Calling the unauthorised action" should {

    val result: Future[Result] = new ErrorsController(cc, mockAppConfig).unauthorised(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }
}
