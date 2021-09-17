package controllers

import play.api.http.Status
import play.api.libs.Files.{SingletonTemporaryFileCreator, TemporaryFile}
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import play.api.test.FakeRequest
import play.api.test.Helpers._
import views.html.AudioPlayerView
import views.html.errors.GenericErrorView

class AudioPlayerControllerSpec extends ControllerBaseSpec {

  val controller =
    new AudioPlayerController(injector.instanceOf[AudioPlayerView], injector.instanceOf[GenericErrorView])

  "Calling the show action" should {

    val result = controller.show(fakeRequest)

    "return 200" in {
      status(result) shouldBe Status.OK
    }

    "return HTML" in {
      contentType(result) shouldBe Some("text/html")
      charset(result) shouldBe Some("utf-8")
    }
  }

  "Calling the upload action" when {

    def uploadRequest(part: FilePart[TemporaryFile]): FakeRequest[MultipartFormData[TemporaryFile]] =
      fakeRequest.withBody(MultipartFormData[TemporaryFile](Map.empty, Seq(part), Nil))

    "an MP3 file has been uploaded" should {

      val file: TemporaryFile = SingletonTemporaryFileCreator.create("")
      val part = FilePart[TemporaryFile]("audio", "cool_song.mp3", None, file)

      val result = controller.upload(uploadRequest(part))

      "return 200" in {
        status(result) shouldBe Status.OK
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "a non-MP3 file has been uploaded" should {

      val file: TemporaryFile = SingletonTemporaryFileCreator.create("")
      val part = FilePart[TemporaryFile]("audio", "cool_document.pdf", None, file)

      val result = controller.upload(uploadRequest(part))

      "return 400" in {
        status(result) shouldBe Status.BAD_REQUEST
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }

    "a file upload attempt is made under an unexpected file key" should {

      val file: TemporaryFile = SingletonTemporaryFileCreator.create("")
      val part = FilePart[TemporaryFile]("what", "cool_song.mp3", None, file)

      val result = controller.upload(uploadRequest(part))

      "return 500" in {
        status(result) shouldBe Status.INTERNAL_SERVER_ERROR
      }

      "return HTML" in {
        contentType(result) shouldBe Some("text/html")
        charset(result) shouldBe Some("utf-8")
      }
    }
  }
}
