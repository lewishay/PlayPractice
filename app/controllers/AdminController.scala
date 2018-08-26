package controllers

import javax.inject.Inject

import common.SessionKeys
import config.AppConfig
import forms.LoginForm
import play.api.mvc._

class AdminController @Inject()(cc: ControllerComponents,
                                implicit val appConfig: AppConfig) extends FrontendController(cc) {

  def admin: Action[AnyContent] = Action { implicit request =>
    request.session.get(SessionKeys.adminStatus) match {
      case Some("true") => Ok(views.html.admin())
      case _ => Redirect(controllers.routes.AdminController.loginShow().url)
    }
  }

  def loginShow: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.login()).removingFromSession(SessionKeys.adminStatus)
  }

  def login: Action[AnyContent] = Action { implicit request =>
    LoginForm.loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      _ => Ok(views.html.admin()).addingToSession(SessionKeys.adminStatus -> "true")
    )
  }
}
