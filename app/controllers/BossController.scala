package controllers

import javax.inject.Inject

import common.Common
import config.AppConfig
import forms.BossForm
import play.api.mvc._
import services.BattleNetService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class BossController @Inject()(battleNetService: BattleNetService)(
                               implicit cc: ControllerComponents,
                               implicit val ec: ExecutionContext,
                               implicit val appConfig: AppConfig) extends FrontendController {

  def blankBoss: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.boss(Common.exampleBoss))
  }

  def getBoss: Action[AnyContent] = Action { implicit request =>
    val formResult = BossForm.bossForm.bindFromRequest
    formResult.fold({ formWithErrors =>
      BadRequest(views.html.boss(Common.exampleBoss, formWithErrors))
    }, { result =>
      Await.result(battleNetService.getBoss(result.id).map { boss =>
        Ok(views.html.boss(boss))
      }.recoverWith {
        case _ => Future.successful(BadRequest(views.html.boss(Common.exampleBoss)))
      }, Duration(10, "seconds"))
    })
  }
}
