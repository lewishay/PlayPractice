package stubs

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import common.Common
import helpers.WireMockMethods
import play.api.http.Status._

object BattleNetStub extends WireMockMethods {

  private val bossUri = "/wow/boss/([0-9]+)"
  private val zoneUri = "/wow/zone/([0-9]+)"

  def successfulBoss: StubMapping = {
    when(method = GET, uri = bossUri).thenReturn(status = OK, body = Common.exampleBoss.toString)
  }

  def successfulZone: StubMapping = {
    when(method = GET, uri = zoneUri).thenReturn(status = OK, body = Common.exampleZone.toString)
  }

  def failureBoss: StubMapping = {
    when(method = GET, uri = bossUri).thenReturn(status = NOT_FOUND, body = failureBody)
  }

  def failureZone: StubMapping = {
    when(method = GET, uri = zoneUri).thenReturn(status = NOT_FOUND, body = failureBody)
  }

  val failureBody = "The resource could not be found."
}
