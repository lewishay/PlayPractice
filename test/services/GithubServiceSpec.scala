package services

import java.io.ByteArrayInputStream

import common.Common
import connectors.GithubConnector
import controllers.ControllerBaseSpec

import scala.io.BufferedSource
import scala.util.{Failure, Success}

class GithubServiceSpec extends ControllerBaseSpec {

  val mockConnector: GithubConnector = mock[GithubConnector]
  val service: GithubService = new GithubService(mockConnector)
  val xmlResponse: BufferedSource = new BufferedSource(new ByteArrayInputStream(Common.exampleXml.getBytes()))
  val emptyResponse: BufferedSource = new BufferedSource(new ByteArrayInputStream("".getBytes()))
  val oneCommit: String =
    """<entry>
      |    <id>tag:github.com,2008:Grit::Commit/8a9d4ef1144009d9387f60a37a47ee26b35b85a0</id>
      |    <link type="text/html" rel="alternate" href=""/>
      |    <title>
      |        Fixed the broken tests
      |    </title>
      |    <updated>2018-03-21T15:34:23Z</updated>
      |    <media:thumbnail height="30" width="30" url=""/>
      |    <author>
      |      <name>user1</name>
      |      <uri>https://github.com/user1</uri>
      |    </author>
      |    <content type="html">
      |      &lt;pre style=&#39;white-space:pre-wrap;width:81ex&#39;&gt;Fixed the broken tests&lt;/pre&gt;
      |    </content>
      |  </entry>
    """.stripMargin.split("\n").map(_.trim).mkString

  "Calling getCommits()" when {

    "the connector returns a successful response" should {

      "return a list of users, dates and commits" in {
        (mockConnector.getCommits(_: String, _: String, _: String))
          .expects(*, *, *)
          .returns(Success(xmlResponse))

        val expected = Some(List(("User: user1", "Date: 2018-03-21", "Commit: Fixed the broken tests"),
                              ("User: user2", "Date: 2018-03-21", "Commit: Created some cool tests")))
        val result = service.getCommits("myOwner", "myRepo", "myBranch")

        result shouldBe expected
      }
    }

    "the connector returns a response with no commits" should {

      "return an empty list" in {
        (mockConnector.getCommits(_: String, _: String, _: String))
          .expects(*, *, *)
          .returns(Success(emptyResponse))

        val result = service.getCommits("myOwner", "myRepo", "myBranch")

        result shouldBe None
      }
    }

    "the connector returns a failure response" should {

      "return an empty list" in {
        (mockConnector.getCommits(_: String, _: String, _: String))
          .expects(*, *, *)
          .returns(Failure(new java.io.FileNotFoundException))

        val result = service.getCommits("myOwner", "myRepo", "myBranch")

        result shouldBe None
      }
    }
  }

  "Calling rawCommits()" when {

    "the input contains commit entries" should {

      val result = service.rawCommits(Common.exampleXml.split("\n").map(_.trim).toList)

      "create a list of two commit entries from the raw Github XML response" in {
        result.length shouldBe 2
      }

      "return valid commit data" in {
        result.head shouldBe oneCommit
      }
    }

    "the input does not contain commit entries" should {

      "return an empty list" in {
        val noCommitResponse: String =
          """"<?xml version="1.0" encoding="UTF-8"?>
            |   <tag>Data</tag>
          """.stripMargin

        val result = service.rawCommits(noCommitResponse.split("\n").map(_.trim).toList)
        result shouldBe List()
      }
    }
  }

  "Calling cleanCommits()" should {

    "return a list of usernames, dates, and commit messages" in {
      val expected = List(("User: user1", "Date: 2018-03-21", "Commit: Fixed the broken tests"))
      val result = service.cleanCommits(List(oneCommit))

      result shouldBe expected
    }
  }
}
