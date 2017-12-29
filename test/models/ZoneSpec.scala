package models

import common.Common
import org.scalatest.{Matchers, WordSpecLike}
import play.api.libs.json.Json

class ZoneSpec extends WordSpecLike with Matchers {

  val exampleString: String = """{"name":"Example","location":"Example"}"""

  "A Zone" should {

    "parse from JSON" in {
      val result = Json.toJson(Common.exampleZone)
      val expectedResult = Json.parse(exampleString)
      result shouldEqual expectedResult
    }

    "be parsed from appropriate JSON" in {
      val result = Json.parse(exampleString).as[Zone]
      result shouldEqual Common.exampleZone
    }
  }
}
