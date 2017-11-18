package controllers

import javax.inject._

import play.api.mvc.{Action, AnyContent}

@Singleton
class HomeController extends BaseController {

  def index: Action[AnyContent] = Action {
    Ok(views.html.index())
  }
}
