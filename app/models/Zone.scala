package models

import play.api.libs.json.{Format, Json}

case class Zone(name: String, location: String)

object Zone {
  implicit val format: Format[Zone] = Json.format[Zone]
}
