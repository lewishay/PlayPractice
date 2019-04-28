package controllers

import javax.inject.Inject

import common.SessionKeys
import config.AppConfig
import forms.{FeatureSwitchForm, LoginFormImpl}
import models.FeatureSwitchModel
import play.api.mvc._
import views.html.admin.{FeatureSwitchView, LoginView}
import views.html.errors.UnauthorisedView

class AdminController @Inject()(loginForm: LoginFormImpl,
                                loginView: LoginView,
                                featureSwitchView: FeatureSwitchView,
                                unauthorisedView: UnauthorisedView)(
                                implicit cc: ControllerComponents,
                                implicit val appConfig: AppConfig) extends FrontendController {

  private def renderFeatureSwitchPage(implicit request: Request[_]): Result =
    Ok(featureSwitchView(
      FeatureSwitchForm.form.fill(FeatureSwitchModel(
        infoBanner = appConfig.features.infoBannerEnabled()
      ))
    ))

  def admin: Action[AnyContent] = Action { implicit request =>
    request.session.get(SessionKeys.adminStatus) match {
      case Some("true") => renderFeatureSwitchPage
      case _ => Unauthorized(unauthorisedView())
    }
  }

  def loginShow: Action[AnyContent] = Action { implicit request =>
    request.session.get(SessionKeys.adminStatus) match {
      case Some("true") => Redirect(controllers.routes.AdminController.admin().url)
      case _ => Ok(loginView(loginForm.form))
    }
  }

  def login: Action[AnyContent] = Action { implicit request =>
    loginForm.form.bindFromRequest().fold(
      formWithErrors => BadRequest(loginView(formWithErrors)),
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

  def clearSession: Action[AnyContent] = Action { implicit request =>
    Redirect(routes.HomeController.home()).withNewSession
  }
}
