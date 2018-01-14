package stubs

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import common.Common
import helpers.WireMockMethods
import play.api.http.Status._
import play.api.libs.json.Json

object BattleNetStub extends WireMockMethods {

  private val bossUri = "/wow/boss/([0-9]+)"
  private val zoneUri = "/wow/zone/([0-9]+)"

  def successfulBoss: StubMapping = {
    when(method = GET, uri = bossUri).thenReturn(status = OK, body = Common.exampleBoss.toString)
  }

  def failureBoss: StubMapping = {
    when(method = GET, uri = bossUri).thenReturn(status = BAD_REQUEST, body = Json.toJson(""))
  }

  def successfulZone: StubMapping = {
    when(method = GET, uri = zoneUri).thenReturn(status = OK, body = Common.exampleZone.toString)
  }

  def failureZone: StubMapping = {
    when(method = GET, uri = zoneUri).thenReturn(status = BAD_REQUEST, body = Json.toJson(""))
  }
}
