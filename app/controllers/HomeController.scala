package controllers

import javax.inject._
import play.api.mvc.{Action, AnyContent, AbstractController, ControllerComponents}

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index: Action[AnyContent] = Action {
    Ok(views.html.index())
  }
}
