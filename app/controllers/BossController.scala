package controllers

import javax.inject.Inject
import common.Common
import forms.BossForm
import models.ErrorModel
import play.api.mvc._
import services.BattleNetService
import views.html.BossView
import views.html.errors.GenericErrorView

import scala.concurrent.{ExecutionContext, Future}

class BossController @Inject()(battleNetService: BattleNetService,
                               bossView: BossView,
                               genericErrorView: GenericErrorView)(
                               implicit cc: ControllerComponents,
                               implicit val ec: ExecutionContext) extends FrontendController {

  def blankBoss: Action[AnyContent] = Action { implicit request =>
    Ok(bossView(Common.exampleBoss))
  }

  def getBoss: Action[AnyContent] = Action.async { implicit request =>
    val formResult = BossForm.bossForm.bindFromRequest
    formResult.fold({ formWithErrors =>
      Future.successful(BadRequest(bossView(Common.exampleBoss, formWithErrors)))
    }, { result =>
      battleNetService.getBoss(result.id).map {
        case Right(boss) =>
          Ok(bossView(boss))
        case Left(ErrorModel(NOT_FOUND, _)) =>
          NotFound(bossView(Common.exampleBoss, notFoundError = true))
        case Left(_) =>
          InternalServerError(genericErrorView())
      }})
  }
}
