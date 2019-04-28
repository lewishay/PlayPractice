package controllers

import javax.inject.Inject

import forms.WordForm
import play.api.mvc._
import views.html.WordSquareView

class WordSquareController @Inject()(wordSquareView: WordSquareView)(
                                     implicit cc: ControllerComponents) extends FrontendController {

  def exampleWordSquare: Action[AnyContent] = Action { implicit request =>
    Ok(wordSquareView("example"))
  }

  def wordSquare: Action[AnyContent] = Action { implicit request =>
    val formResult = WordForm.wordForm.bindFromRequest
    formResult.fold({ formWithErrors =>
      BadRequest(wordSquareView("error", formWithErrors))
    }, { result =>
      Ok(wordSquareView(result.word))
    })
  }
}
