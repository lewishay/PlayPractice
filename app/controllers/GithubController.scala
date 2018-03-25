package controllers

import javax.inject.Inject

import models.viewModels.GithubViewModel
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.GithubService

class GithubController @Inject()(cc: ControllerComponents, githubService: GithubService)
  extends AbstractController(cc) with I18nSupport {

  def commitsPage: Action[AnyContent] = Action { implicit request =>
    val commitLog1 = githubService.getCommits("hmrc", "manage-vat-subscription-frontend", "master")
    val commitLog2 = githubService.getCommits("hmrc", "view-vat-returns-frontend", "master")
    val commitLog3 = githubService.getCommits("hmrc", "vat-summary-frontend", "master")
    val commitLog4 = githubService.getCommits("hmrc", "deregister-vat-frontend", "master")

    val viewModel = GithubViewModel(commitLog1, commitLog2, commitLog3, commitLog4)

    Ok(views.html.githubCommits(viewModel))
  }
}
