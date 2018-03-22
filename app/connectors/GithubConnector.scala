package connectors

import javax.inject.Inject

import config.AppConfig
import play.api.libs.ws.{WSClient, WSRequest}

import scala.concurrent.{ExecutionContext, Future}

class GithubConnector @Inject()(ws: WSClient, appConfig: AppConfig) {

  private[connectors] def getUrl(repoOwner: String, repo: String, branch: String) =
    s"${appConfig.protocol}://${appConfig.battleNetService}/$repoOwner/$repo/commits/$branch.atom"

  def getCommits(repoOwner: String, repo: String, branch: String)(implicit ec: ExecutionContext): Future[String] = {
    val request: WSRequest = ws.url(getUrl(repoOwner, repo, branch))
    request.get().map { req =>
      req.body
    }
  }
}