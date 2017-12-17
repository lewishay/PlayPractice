package models

import common.Common
import org.scalatest.{Matchers, WordSpecLike}
import play.api.libs.json.Json

class BossSpec extends WordSpecLike with Matchers {

  val exampleString: String =
    """{
      |"name":"Example",
      |"description":"Example",
      |"health":0,
      |"level":0,
      |"zone":{"name":"Example","location":"Example"}
      |}"""
      .stripMargin.replace("\n", "")

  "A Boss" should {

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
