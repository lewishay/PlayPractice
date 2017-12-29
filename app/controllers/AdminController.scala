package controllers

import javax.inject.Inject

import controllers.auth.AuthenticatedAction
import play.api.mvc._

class AdminController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def admin: Action[AnyContent] = AuthenticatedAction {
    Ok(views.html.admin())
  }
}
