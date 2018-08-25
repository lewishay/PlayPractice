package controllers

import javax.inject.Inject

import config.AppConfig
import play.api.mvc._

class ErrorsController @Inject()(cc: ControllerComponents,
                                 implicit val appConfig: AppConfig) extends FrontendController(cc) {

  def unauthorised: Action[AnyContent] = Action {
    Ok(views.html.errors.unauthorised())
  }
}
