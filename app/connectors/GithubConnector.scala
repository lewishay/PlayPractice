package connectors

import javax.inject.Inject

import config.AppConfig
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}

import scala.concurrent.Future

class GithubConnector @Inject()(repoOwner: String, repo: String, branch: String, ws: WSClient, appConfig: AppConfig) {

  private[connectors] def getUrl =
    s"${appConfig.protocol}://${appConfig.battleNetService}/$repoOwner/$repo/commits/$branch.atom"

  def getCommits: Future[WSResponse] = {
    val request: WSRequest = ws.url(getUrl)
    request.get()
  }
}