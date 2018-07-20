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

package utils

import games.hangman.HangmanGameState
import play.api.mvc.QueryStringBindable

import scala.util.{Failure, Success, Try}

object Binders {

  implicit object HangmanBindable extends QueryStringBindable[HangmanGameState] {

    def bind(key: String, params: Map[String, Seq[String]]) = {
      val result: Try[String] = Try(params.get(key).flatMap(_.headOption).get)
      result match {
        case Success(value) => Right(value)
        case Failure(_) => Left("Cannot parse parameter" + key " as HangmanGameState")
      }
    }

    def unbind(key: String, value: HangmanGameState): String = key + "=" + value.toString
  }
}
