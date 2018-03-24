package controllers

import javax.inject.Inject

import models.viewModels.GithubViewModel
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.GithubService

class GithubController @Inject()(cc: ControllerComponents, githubService: GithubService)
  extends AbstractController(cc) with I18nSupport {

  def commitsPage: Action[AnyContent] = Action { implicit request =>
    val repo1 = githubService.getCommits("hmrc", "manage-vat-subscription-frontend", "master")
    val repo2 = githubService.getCommits("hmrc", "view-vat-returns-frontend", "master")
    val repo3 = githubService.getCommits("hmrc", "vat-summary-frontend", "master")
    val repo4 = githubService.getCommits("hmrc", "deregister-vat-frontend", "master")

    val viewModel = GithubViewModel(repo1, repo2, repo3, repo4)

    Ok(views.html.githubCommits(viewModel))
  }
}
