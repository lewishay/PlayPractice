package connectors

import javax.inject.Inject

import config.AppConfig

import scala.io.{BufferedSource, Source}

class GithubConnector @Inject()(appConfig: AppConfig) {

  private[connectors] def getUrl(repoOwner: String, repo: String, branch: String) =
    s"${appConfig.protocol}://${appConfig.githubService}/$repoOwner/$repo/commits/$branch.atom"

  def getCommits(repoOwner: String, repo: String, branch: String): BufferedSource = {
    val url = getUrl(repoOwner, repo, branch)
    Source.fromURL(url)
  }
}