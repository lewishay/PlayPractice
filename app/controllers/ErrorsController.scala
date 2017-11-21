package controllers

import javax.inject.Inject

import play.api.mvc._

class ErrorsController @Inject()(cc: ControllerComponents) extends AbstractController(cc)  {

  def unauthorised: Action[AnyContent] = Action {
    Ok(views.html.unauthorised())
  }
}
