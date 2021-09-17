package models

import common.Common
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import play.api.libs.json.Json

class BossSpec extends AnyWordSpecLike with Matchers {

  val exampleString: String =
    """{
      |"name":"Example",
      |"description":"Example",
      |"health":0,
      |"level":0,
      |"zone":{"name":"Example","location":"Example"}
      |}"""
      .stripMargin

  "A Boss" should {

    "parse from JSON" in {
      val result = Json.toJson(Common.exampleBoss)
      val expectedResult = Json.parse(exampleString)
      result shouldEqual expectedResult
    }

    "be parsed from appropriate JSON" in {
      val result = Json.parse(exampleString).as[Boss]
      result shouldEqual Common.exampleBoss
    }
  }
}
