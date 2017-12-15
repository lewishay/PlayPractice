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

package models

import common.Common
import org.scalatest.{Matchers, WordSpecLike}
import play.api.libs.json.Json

class BossSpec  extends WordSpecLike with Matchers {

  "A Boss" should {

    val exampleString =
      """{
        |"name":"Example",
        |"description":"Example",
        |"health":0,
        |"level":0,
        |"zone":{"name":"Example","location":"Example"}
        |}"""
        .stripMargin.replace("\n", "")

    "parse from JSON" in {
      val result = Json.toJson(Common.exampleBoss).toString
      result shouldEqual exampleString
    }

    "be parsed from appropriate JSON" in {
      val result = Json.parse(exampleString).as[Boss]
      result shouldEqual Common.exampleBoss
    }
  }
}
