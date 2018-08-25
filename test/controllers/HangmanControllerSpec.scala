package controllers

import games.hangman.HangmanGameState
import models.viewModels.HangmanViewModel
import play.api.http.Status
import play.api.test.Helpers._

class HangmanControllerSpec extends ControllerBaseSpec {

  val controller = new HangmanController(cc, mockAppConfig)

  "Calling the hangman action" should {

    "return 200" in {
      val result = controller.hangman(fakeRequest)
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      val result = controller.hangman(fakeRequest)
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the makeGuess action" when {

    val exampleViewModel = HangmanViewModel(
      HangmanGameState(
        "water",
        0,
        8,
        Vector[Char](),
        Vector.fill(5)('_'),
        Vector[String]()
      ),
      None
    )

    "there are no errors in the form" should {

      "return 200" in {
        val result = controller.makeGuess(exampleViewModel)(fakeRequest.withFormUrlEncodedBody("guess" -> "x"))
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        val result = controller.makeGuess(exampleViewModel)(fakeRequest.withFormUrlEncodedBody("guess" -> "x"))
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "there are errors in the form" should {

      "return 400" in {
        val result = controller.makeGuess(exampleViewModel)(fakeRequest.withFormUrlEncodedBody("guess" -> "xxx"))
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        val result = controller.makeGuess(exampleViewModel)(fakeRequest.withFormUrlEncodedBody("guess" -> "xxx"))
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }
}
