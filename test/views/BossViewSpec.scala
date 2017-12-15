/*
 * Copyright 2017 HM Revenue & Customs
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

import common.Common
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class BossViewSpec extends ViewBaseSpec {

  object Selectors {
    val title = "h1"
    val formLabel = ".big-form > dl > dt > label"
    val formButton = ".big-form > button"
  }

  lazy val view = views.html.boss(Common.exampleBoss)
  lazy implicit val document: Document = Jsoup.parse(view.body)

  "The boss page" should {

    "have the correct title" in {
      elementText(Selectors.title) shouldBe "Boss API call"
    }

    "have the correct form label text" in {
      elementText(Selectors.formLabel) shouldBe "Select boss:"
    }

    "have the correct button text" in {
      elementText(Selectors.formButton) shouldBe "Get boss!"
    }
  }
}
