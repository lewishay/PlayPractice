package controllers

import play.api.mvc.{Action, AnyContent}

class ErrorsController extends BaseController {

  def unauthorised: Action[AnyContent] = Action {
    Ok(views.html.unauthorised())
  }
}
