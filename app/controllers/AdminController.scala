package controllers

import javax.inject.Inject

import common.SessionKeys
import config.AppConfig
import forms.{FeatureSwitchForm, LoginForm}
import models.FeatureSwitchModel
import play.api.mvc._

class AdminController @Inject()(cc: ControllerComponents,
                                implicit val appConfig: AppConfig) extends FrontendController(cc) {

  private def renderFeatureSwitchPage(implicit request: Request[_]): Result =
    Ok(views.html.admin.featureSwitch(
      FeatureSwitchForm.form.fill(FeatureSwitchModel(
        infoBanner = appConfig.features.infoBannerEnabled()
      ))
    ))

  def admin: Action[AnyContent] = Action { implicit request =>
    request.session.get(SessionKeys.adminStatus) match {
      case Some("true") => renderFeatureSwitchPage
      case _ => Redirect(controllers.routes.AdminController.loginShow().url)
    }
  }

  def loginShow: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.admin.login()).removingFromSession(SessionKeys.adminStatus)
  }

  def login: Action[AnyContent] = Action { implicit request =>
    LoginForm.loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.admin.login(formWithErrors)),
      _ => renderFeatureSwitchPage.addingToSession(SessionKeys.adminStatus -> "true")
    )
  }

  def submitFeatures: Action[AnyContent] = Action { implicit request =>
    FeatureSwitchForm.form.bindFromRequest().fold(
      _ => Redirect(routes.AdminController.admin()),
      successModel => {
        appConfig.features.infoBannerEnabled(successModel.infoBanner)
        Redirect(routes.AdminController.admin())
      }
    )
  }
}
