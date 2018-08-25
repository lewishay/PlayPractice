package controllers

import javax.inject.Inject

import config.AppConfig
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, ControllerComponents}

abstract class FrontendController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  implicit val appConfig: AppConfig
}
