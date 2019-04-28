package controllers

import play.api.http.Status
import play.api.mvc.Result
import play.api.test.Helpers._
import views.html.WordSquareView

import scala.concurrent.Future

class WordSquareControllerSpec extends ControllerBaseSpec {

  val wordSquareView: WordSquareView = injector.instanceOf[WordSquareView]

  "Calling the exampleWordSquare action" should {

    val result: Future[Result] =
      new WordSquareController(wordSquareView).exampleWordSquare(fakeRequest)

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

      val result: Future[Result] = new WordSquareController(wordSquareView).wordSquare(
        fakeRequest.withFormUrlEncodedBody(
          "word" -> "TEST"
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

      val result: Future[Result] = new WordSquareController(wordSquareView).wordSquare(
        fakeRequest.withFormUrlEncodedBody(
          "word" -> "££!££$$%^%$$"
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
