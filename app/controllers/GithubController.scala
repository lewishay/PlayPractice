package controllers

import javax.inject.Inject

import models.viewModels.GithubViewModel
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.GithubService

import scala.concurrent.{ExecutionContext, Future}

class GithubController @Inject()(cc: ControllerComponents, githubService: GithubService, implicit val ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {

  def commitsPage: Action[AnyContent] = Action { implicit request =>
    val repo1Call = githubService.getCommits("hmrc", "view-vat-returns-frontend", "master")
    val repo2Call = githubService.getCommits("hmrc", "vat-summary-frontend", "master")
    val repo3Call = githubService.getCommits("hmrc", "view-vat-acceptance-tests", "master")
    val repo4Call = githubService.getCommits("hmrc", "view-vat-performance-tests", "master")

    val result: Future[GithubViewModel] = for {
      repo1 <- repo1Call
      repo2 <- repo2Call
      repo3 <- repo3Call
      repo4 <- repo4Call
    } yield GithubViewModel(repo1, repo2, repo3, repo4)

    result.map { viewModel =>
      Ok(views.html.githubCommits(viewModel))
    }
  }
}
