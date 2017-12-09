package controllers

import play.api.http.Status
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class WordSquareControllerSpec extends ControllerBaseSpec {

  "Calling the exampleWordSquare action" should {

    val result: Future[Result] = new WordSquareController(cc).exampleWordSquare(FakeRequest())

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the wordSquare action" when {

    "there are no errors in the form" should {

      val result: Future[Result] = new WordSquareController(cc).wordSquare(
        FakeRequest().withFormUrlEncodedBody(
          ("word", "TEST")
        )
      )

      "return 200" in {
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "there are errors in the form" should {

      val result: Future[Result] = new WordSquareController(cc).wordSquare(
        FakeRequest().withFormUrlEncodedBody(
          ("word", "££!££$$%^%$$")
        )
      )

      "return 400" in {
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }
}
