package controllers

import javax.inject._

import play.api.mvc._
import views.html.HomeView

class HomeController @Inject()(homeView: HomeView)(implicit cc: ControllerComponents) extends FrontendController {

  def home: Action[AnyContent] = Action {
    Ok(homeView())
  }
}
