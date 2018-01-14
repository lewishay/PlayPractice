package controllers

import play.api.http.Status
import play.api.mvc.Result
import play.api.test.Helpers._

import scala.concurrent.Future

class PixelGridControllerSpec extends ControllerBaseSpec {

  val controller: PixelGridController = new PixelGridController(cc)

  "Calling the checkGrid action" should {
    val result: Future[Result] = controller.checkGrid(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the scalaGrid action" should {
    val result: Future[Result] = controller.scalaGrid(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }
}
