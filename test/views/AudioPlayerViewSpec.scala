/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package views

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import views.html.AudioPlayerView

import scala.util.Try

class AudioPlayerViewSpec extends ViewBaseSpec {

  val injectedView: AudioPlayerView = injector.instanceOf[AudioPlayerView]

  object Selectors {
    val title = "h1"
    val subheading = "h2"
    val input = ".big-form > dd > input"
    val uploadButton = ".big-form > div > button"
    val audioControls = "audio"
    val audioSource = "source"
    val formError = ".error"
  }

  "The audio player page" when {

    "there is no file uploaded and no errors in the form" should {

      lazy implicit val document: Document = Jsoup.parse(injectedView().body)

      "have the correct title" in {
        elementText(Selectors.title) shouldBe "Audio player"
      }

      "have the correct subheading" in {
        elementText(Selectors.subheading) shouldBe "Upload an MP3 file to enable audio controls"
      }

      "have a file input element" in {
        element(Selectors.input).attr("type") shouldBe "file"
      }

      "have an upload button" in {
        elementText(Selectors.uploadButton) shouldBe "Upload"
      }

      "have audio controls" in {
        Try(element(Selectors.audioControls)).isSuccess
      }

      "not have an audio source" in {
        Try(element(Selectors.audioSource)).isFailure
      }
    }
  }

  "there is a file uploaded and no errors in the form" should {

    lazy val view = injectedView(filePath = Some("myFile.mp3"))
    lazy implicit val document: Document = Jsoup.parse(view.body)

    "have an audio source" in {
      Try(element(Selectors.audioSource)).isSuccess
    }
  }

  "there are errors in the form" should {

    lazy val view = injectedView(formError = true)
    lazy implicit val document: Document = Jsoup.parse(view.body)

    "show an error message" in {
      elementText(Selectors.formError) shouldBe "The file must be of type .mp3"
    }
  }
}
