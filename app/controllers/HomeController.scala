package controllers

import javax.inject._

import config.AppConfig
import play.api.mvc._

class HomeController @Inject()(implicit cc: ControllerComponents,
                               implicit val appConfig: AppConfig) extends FrontendController {

  def home: Action[AnyContent] = Action {
    Ok(views.html.home())
  }
}
