package connectors

import com.github.tomakehurst.wiremock.stubbing.StubMapping
import helpers.IntegrationBaseSpec
import stubs.GithubStub

class GithubConnectorISpec extends IntegrationBaseSpec {

  val connector: GithubConnector = app.injector.instanceOf[GithubConnector]

  "Calling getCommits with valid information" should {

    "return a Success" in {
      def setupStubs(): StubMapping = GithubStub.successCommitLog
      setupStubs()
      val result = connector.getCommits("owner", "repo", "branch")
      result.isSuccess shouldBe true
    }

    "return XML data for the chosen branch" in {
      def setupStubs(): StubMapping = GithubStub.successCommitLog
      setupStubs()
      val result = connector.getCommits("owner", "repo", "branch")
      val xml = result.map { response =>
        response.getLines.map(_.trim).toList
      }
      xml.getOrElse(List()).head shouldBe """<?xml version="1.0" encoding="UTF-8"?>"""
    }
  }

  "Calling getCommits with invalid information" should {

    "return a Failure" in {
      def setupStubs(): StubMapping = GithubStub.failCommitLog
      setupStubs()
      val result = connector.getCommits("owner", "repo", "branch")
      result.isFailure shouldBe true
    }
  }
}
