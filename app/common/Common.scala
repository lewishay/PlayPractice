package common

import common.Grids._
import models.{Boss, PixelGrid, Zone}

object Common {

  val exampleZone: Zone = Zone("Example", "Example")
  val exampleBoss: Boss = Boss("Example", "Example", 0, 0, exampleZone)
  val gridList: Seq[PixelGrid] = Seq(checkedGrid, scalaClass, oneUpMushroom, corruption)

  val exampleXml: String =
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
}
