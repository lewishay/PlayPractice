package models

import common.Common
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike
import play.api.libs.json.Json

class ZoneSpec extends AnyWordSpecLike with Matchers {

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
