package controllers

import javax.inject.Inject

import games.hangman.{Hangman, HangmanGameState}
import forms.GuessForm
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class HangmanController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def hangman: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.hangman(Some(HangmanGameState(
      "hey", 4, 5, Vector('h', 'e', 'y'), Vector('_', '_', 'n'), Vector('o'), Vector("  _______ ", "  |     | ")
    ))))
  }

  def makeGuess: Action[AnyContent] = Action { implicit request =>
    val formValidationResult = GuessForm.makeGuessForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.hangman(None, formWithErrors))
    }, { result =>
      Hangman.makeMove(result.guess)
      Redirect(routes.HangmanController.hangman())
    })
  }
}
