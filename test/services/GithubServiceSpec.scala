package services

import connectors.GithubConnector
import controllers.ControllerBaseSpec

import scala.concurrent.{ExecutionContext, Future}

class GithubServiceSpec extends ControllerBaseSpec {

  val mockConnector: GithubConnector = mock[GithubConnector]
  implicit val ec: ExecutionContext = ExecutionContext.global
  val service: GithubService = new GithubService(mockConnector)

  val response: String =
    """<?xml version="1.0" encoding="UTF-8"?>
      |<feed xmlns="http://www.w3.org/2005/Atom" xmlns:media="http://search.yahoo.com/mrss/" xml:lang="en-US">
      |  <id>tag:github.com,2008:/myOwner/myRepo/commits/myBranch</id>
      |  <link type="text/html" rel="alternate" href="https://github.com/myOwner/myRepo/commits/myBranch.atom"/>
      |  <link type="application/atom+xml" rel="self" href="https://github.com/myOwner/myRepo/commits/myBranch.atom"/>
      |  <title>Recent Commits to myRepo:myBranch</title>
      |  <updated>2018-03-21T15:34:23Z</updated>
      |  <entry>
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
      |  <entry>
      |    <id>tag:github.com,2008:Grit::Commit/e2dc3b2a264f1f99da9f54f99e31f813495e24a0</id>
      |    <link type="text/html" rel="alternate" href=""/>
      |    <title>
      |        Created some cool tests
      |    </title>
      |    <updated>2018-03-21T14:39:03Z</updated>
      |    <media:thumbnail height="30" width="30" url=""/>
      |    <author>
      |      <name>user2</name>
      |      <uri>https://github.com/user2</uri>
      |    </author>
      |    <content type="html">
      |      &lt;pre style=&#39;white-space:pre-wrap;width:81ex&#39;&gt;Created some cool tests&lt;/pre&gt;
      |    </content>
      |  </entry>
    """.stripMargin

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
        (mockConnector.getCommits(_: String, _: String, _: String)(_: ExecutionContext))
          .expects(*, *, *, *)
          .returns(Future.successful(response))

        val expected = List(("User: user1", "Date: 2018-03-21", "Commit: Fixed the broken tests"),
                              ("User: user2", "Date: 2018-03-21", "Commit: Created some cool tests"))
        val result = await(service.getCommits("myOwner", "myRepo", "myBranch"))

        result shouldBe expected
      }
    }

    "the connector returns a failure response" should {

    }
  }


  "Calling rawCommits()" when {

    "the input contains commit entries" should {

      val result = service.rawCommits(response.split("\n").map(_.trim).toList)

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
