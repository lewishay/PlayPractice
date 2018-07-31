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

package models.viewModels

import games.hangman.HangmanGameState
import org.scalatest.{Matchers, WordSpecLike}

class HangmanViewModelSpec extends WordSpecLike with Matchers {

  val viewModel = HangmanViewModel(
    HangmanGameState(
      "water",
      1,
      7,
      Vector('x'),
      Vector('_', '_', '_', '_', '_'),
      Vector("  _______ ")
    ),
    None
  )
  val gameString = "119;97;116;101;114;-1-7-x-95;95;95;95;95;-  _______ -none"

  "Calling the parseHangman function" should {

    "correctly parse a String to a HangmanViewModel" in {
      HangmanViewModel.parseHangman(gameString) shouldBe viewModel
    }
  }

  "Calling the serializeHangman function" should {

    "correctly serialize a HangmanViewModel to a String" in {
      HangmanViewModel.serializeHangman(viewModel) shouldBe gameString
    }
  }

  "Calling the encode function" should {

    "encode a string by converting its characters to their integer values and appending separators" in {
      HangmanViewModel.encode("water") shouldBe "119;97;116;101;114;"
    }
  }

  "Calling the decode function" should {

    val exampleArr = Array("119", "97", "116", "101", "114")

    "decode an Array of Strings by converting the integer values to characters and gluing them together" in {
      HangmanViewModel.decode(exampleArr) shouldBe "water"
    }
  }

  "Calling the bind function in the custom query string binder" when {

    "the binding is successful" should {

      "return a Right wrapped in an Option" in {
        HangmanViewModel.queryStringBindable.bind("", Map(gameString -> Seq())) shouldBe Some(Right(viewModel))
      }
    }

    "the binding is unsuccessful" should {

      "return a Left wrapped in an Option" in {
        HangmanViewModel.queryStringBindable.bind("", Map("junk" -> Seq())) shouldBe
          Some(Left("Unable to bind HangmanViewModel"))
      }
    }
  }
}
