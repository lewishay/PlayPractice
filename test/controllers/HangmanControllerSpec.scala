package controllers

import games.hangman.HangmanGameState
import models.viewModels.HangmanViewModel
import play.api.http.Status
import play.api.test.Helpers._

class HangmanControllerSpec extends ControllerBaseSpec {

  val controller = new HangmanController()

  "Calling the hangman action" should {

    val result = controller.hangman(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
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

      val result = controller.makeGuess(exampleViewModel)(fakeRequest.withFormUrlEncodedBody("guess" -> "x"))

      "return 200" in {
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "there are errors in the form" should {

      val result = controller.makeGuess(exampleViewModel)(fakeRequest.withFormUrlEncodedBody("guess" -> "xxx"))

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
