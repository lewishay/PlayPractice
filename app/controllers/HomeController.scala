package controllers

import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc._

@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def home: Action[AnyContent] = Action {
    Ok(views.html.home())
  }
}
