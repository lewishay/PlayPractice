package controllers

import javax.inject.Inject

import models.viewModels.GithubViewModel
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import services.GithubService
import views.html.GithubCommitsView

class GithubController @Inject()(githubService: GithubService,
                                 githubCommitsView: GithubCommitsView)(
                                 implicit cc: ControllerComponents) extends FrontendController {

  def commitsPage: Action[AnyContent] = Action { implicit request =>
    val commitLog1 = githubService.getCommits("hmrc", "manage-vat-subscription-frontend", "master")
    val commitLog2 = githubService.getCommits("hmrc", "view-vat-returns-frontend", "master")
    val commitLog3 = githubService.getCommits("hmrc", "vat-summary-frontend", "master")
    val commitLog4 = githubService.getCommits("hmrc", "deregister-vat-frontend", "master")

    val viewModel = GithubViewModel(commitLog1, commitLog2, commitLog3, commitLog4)

    Ok(githubCommitsView(viewModel))
  }
}
