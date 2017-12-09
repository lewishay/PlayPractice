package controllers

import javax.inject.Inject

import models.{Boss, BossForm, Zone}
import play.api.i18n.I18nSupport
import play.api.mvc._
import services.BattleNetService

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class BossController @Inject()(cc: ControllerComponents, battleNetService: BattleNetService, implicit val ec: ExecutionContext)
  extends AbstractController(cc) with I18nSupport {

  def blankBoss: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.boss(Boss.blankBoss))
  }

  def getBoss: Action[AnyContent] = Action { implicit request =>
    val formResult = BossForm.bossForm.bindFromRequest
    formResult.fold({ formWithErrors =>
      BadRequest(views.html.boss(Boss.blankBoss, formWithErrors))
    }, { result =>
      Await.result(battleNetService.getBoss(result.id).map { boss =>
        Ok(views.html.boss(boss))
      }.recoverWith {
        case _ => Future.successful(BadRequest(views.html.boss(Boss.blankBoss)))
      }, Duration(10, "seconds"))
    })
  }
}
