package controllers

import javax.inject.Inject

import config.AppConfig
import forms.WordForm
import play.api.mvc._

class WordSquareController @Inject()(cc: ControllerComponents,
                                     implicit val appConfig: AppConfig) extends FrontendController(cc) {

def exampleWordSquare: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.wordSquare("example"))
  }

  def wordSquare: Action[AnyContent] = Action { implicit request =>
    val formResult = WordForm.wordForm.bindFromRequest
    formResult.fold({ formWithErrors =>
      BadRequest(views.html.wordSquare("error", formWithErrors))
    }, { result =>
      Ok(views.html.wordSquare(result.word))
    })
  }
}
