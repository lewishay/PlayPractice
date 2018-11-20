package controllers

import javax.inject.Inject

import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, ControllerComponents}

abstract class FrontendController @Inject()(implicit val cc: ControllerComponents) extends
  AbstractController(cc) with I18nSupport
