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

  def viewModel(gameWin: Option[Boolean]): HangmanViewModel = HangmanViewModel(
    HangmanGameState(
      "water",
      1,
      7,
      Vector('x'),
      Vector('_', '_', '_', '_', '_'),
      Vector("  _______ ")
    ),
    gameWin
  )

  def gameString(gameWin: Option[Boolean]): String = gameWin match {
    case Some(result) => s"119;97;116;101;114;-1-7-x-_____-  _______ -$result"
    case None => "119;97;116;101;114;-1-7-x-_____-  _______ -none"
  }

  "Calling the parseHangman function" when {

    "a game is in progress" should {

      "correctly parse a String to a HangmanViewModel" in {
        HangmanViewModel.parseHangman(gameString(None)) shouldBe viewModel(None)
      }
    }

    "a game is won" should {

      "correctly parse a String to a HangmanViewModel" in {
        HangmanViewModel.parseHangman(gameString(Some(true))) shouldBe viewModel(Some(true))
      }
    }

    "a game is lost" should {

      "correctly parse a String to a HangmanViewModel" in {
        HangmanViewModel.parseHangman(gameString(Some(false))) shouldBe viewModel(Some(false))
      }
    }
  }

  "Calling the serializeHangman function" should {

    "correctly serialize a HangmanViewModel to a String" in {
      HangmanViewModel.serializeHangman(viewModel(None)) shouldBe gameString(None)
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
        HangmanViewModel.queryStringBindable.bind("", Map(gameString(None) -> Seq())) shouldBe Some(Right(viewModel(None)))
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
