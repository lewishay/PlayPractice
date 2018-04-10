package controllers

import play.api.http.Status
import play.api.test.Helpers._

class HangmanControllerSpec extends ControllerBaseSpec {

  val controller = new HangmanController(cc)

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

    "there are no errors in the form" should {

      "redirect to the hangman action" in {
        val result = controller.makeGuess(fakeRequest.withFormUrlEncodedBody("guess" -> "x"))
        redirectLocation(result) shouldBe Some(routes.HangmanController.hangman().url)
      }
    }

    "there are errors in the form" should {

      "return 400" in {
        val result = controller.makeGuess(fakeRequest.withFormUrlEncodedBody("guess" -> "xxx"))
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        val result = controller.makeGuess(fakeRequest.withFormUrlEncodedBody("guess" -> "xxx"))
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }
}
