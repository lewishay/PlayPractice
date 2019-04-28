package controllers

import play.api.http.Status
import play.api.mvc.Result
import play.api.test.Helpers._
import views.html.PixelGridView

import scala.concurrent.Future

class PixelGridControllerSpec extends ControllerBaseSpec {

  val controller: PixelGridController = new PixelGridController(injector.instanceOf[PixelGridView])

  "Calling the defaultGrid action" should {
    val result: Future[Result] = controller.defaultGrid(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the loadGrid action" should {
    val result: Future[Result] = controller.loadGrid("Some grid")(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }
}
