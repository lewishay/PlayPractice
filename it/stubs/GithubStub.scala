package stubs

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import common.Common
import helpers.WireMockMethods
import play.api.http.Status._

object GithubStub extends WireMockMethods {

  private val githubUri = "(.*)/(.*)/commits/(.*).atom"

  def successCommitLog: StubMapping = {
    when(method = GET, uri = githubUri).thenReturn(status = OK, body = commitLog)
  }

  def failCommitLog: StubMapping = {
    when(method = GET, uri = githubUri).thenReturn(status = NOT_FOUND)
  }

  val commitLog: String = Common.exampleXml
}
