package controllers

import javax.inject.Inject

import common.Common
import config.AppConfig
import forms.BossForm
import models.ErrorModel
import play.api.mvc._
import services.BattleNetService

import scala.concurrent.{ExecutionContext, Future}

class BossController @Inject()(battleNetService: BattleNetService)(
                               implicit cc: ControllerComponents,
                               implicit val ec: ExecutionContext,
                               implicit val appConfig: AppConfig) extends FrontendController {

  def blankBoss: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.boss(Common.exampleBoss))
  }

  def getBoss: Action[AnyContent] = Action.async { implicit request =>
    val formResult = BossForm.bossForm.bindFromRequest
    formResult.fold({ formWithErrors =>
      Future.successful(BadRequest(views.html.boss(Common.exampleBoss, formWithErrors)))
    }, { result =>
      battleNetService.getBoss(result.id).map {
        case Right(boss) => Ok(views.html.boss(boss))
        case Left(ErrorModel(NOT_FOUND, _)) => NotFound(views.html.boss(Common.exampleBoss, notFoundError = true))
        case Left(_) => InternalServerError(views.html.errors.genericError())
    }})
  }
}
