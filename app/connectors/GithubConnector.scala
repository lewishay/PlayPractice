package connectors

import javax.inject.Inject

import config.AppConfig

import scala.io.{BufferedSource, Source}
import scala.util.Try

class GithubConnector @Inject()(appConfig: AppConfig) {

  private[connectors] def getUrl(repoOwner: String, repo: String, branch: String) =
    s"${appConfig.protocol}://${appConfig.githubService}/$repoOwner/$repo/commits/$branch.atom"

  def getCommits(repoOwner: String, repo: String, branch: String): Try[BufferedSource] = {
    val url = getUrl(repoOwner, repo, branch)
    Try(Source.fromURL(url))
  }
}