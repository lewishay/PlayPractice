package services

import javax.inject.Inject

import connectors.GithubConnector

import scala.util.Success
import scala.util.matching.Regex

class GithubService @Inject()(connector: GithubConnector) {

  def getCommits(repoOwner: String, repo: String, branch: String): Option[List[(String, String, String)]] = {
    connector.getCommits(repoOwner, repo, branch) match {
      case Success(rawXml) =>
        val log = rawXml.getLines.map(_.trim).toList
        val commitLog = rawCommits(log)
        if(commitLog.nonEmpty) Some(cleanCommits(commitLog)) else None
      case _ => None
    }
  }

  private[services] def rawCommits(list: List[String], newList: List[String] = List()): List[String] = list.headOption match {
    case None => newList
    case Some("<entry>") => rawCommits(list.tail, newList :+ list.splitAt(list.indexOf("</entry>") + 1)._1.mkString)
    case _ => rawCommits(list.tail, newList)
  }

  private[services] def cleanCommits(list: List[String]): List[(String, String, String)] = {
    val userRegex = """<name>(.*)</name>""".r
    val dateRegex = """<updated>(.*)</updated>""".r
    val commitRegex = """<title>(.*)</title>""".r
    list.map { item =>
      ("User: " + regexHelper(userRegex, item),
        "Date: " + regexHelper(dateRegex, item).take(10),
        "Commit: " + regexHelper(commitRegex, item).replace("&#39;", "'"))
    }
  }

  private[services] def regexHelper(regex: Regex, item: String): String = {
    val result = regex.findAllIn(item)
    result.hasNext
    result.group(1)
  }
}