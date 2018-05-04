package services

import javax.inject.Inject

import connectors.GithubConnector
import models.CommitLog

import scala.util.matching.Regex

class GithubService @Inject()(connector: GithubConnector) {

  def getCommits(repoOwner: String, repo: String, branch: String): Option[CommitLog] = {
    val connectorResult = connector.getCommits(repoOwner, repo, branch)
    if(connectorResult.nonEmpty) {
      val log = connectorResult.getLines.map(_.trim).toList
      val commitLog = rawCommits(log)
      if (commitLog.nonEmpty) {
        Some(CommitLog(repo, branch, cleanCommits(commitLog)))
      } else {
        None
      }
    } else {
      None
    }
  }

  private[services] def rawCommits(list: List[String], newList: List[String] = List()): List[String] = list.headOption match {
    case None => newList
    case Some("<entry>") => rawCommits(list.tail, newList :+ list.splitAt(list.indexOf("</entry>") + 1)._1.mkString)
    case _ => rawCommits(list.tail, newList)
  }

  private[services] def cleanCommits(list: List[String]): List[(String, String, String, String)] = {
    val avatarRegex = """<uri>(.*)</uri>""".r
    val userRegex = """<name>(.*)</name>""".r
    val dateRegex = """<updated>(.*)</updated>""".r
    val commitRegex = """<title>(.*)</title>""".r
    list.map { item =>
      (
        regexHelper(avatarRegex, item) + ".png",
        regexHelper(userRegex, item),
        dateTimeFormat(regexHelper(dateRegex, item)),
        new String(regexHelper(commitRegex, item).getBytes, "UTF-8").replace("&#39;", "'")
      )
    }
  }

  private[services] def regexHelper(regex: Regex, item: String): String = {
    val result = regex.findAllIn(item)
    result.hasNext
    result.group(1)
  }

  private[services] def dateTimeFormat(timecode: String): String = {
    val dateTimeSplit: (String, String) = timecode.dropRight(4).splitAt(10)
    s"${dateTimeSplit._1}, ${dateTimeSplit._2.drop(1)}"
  }
}
