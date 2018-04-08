package controllers

import javax.inject.Inject

import games.hangman.Hangman
import forms.GuessForm
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class HangmanController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def hangman: Action[AnyContent] = Action { implicit request =>
    if(!Hangman.gameStarted) {
      Hangman.newGame()
      makeGuess
    }
    Ok(views.html.hangman(GuessForm.makeGuessForm))
  }

  def makeGuess: Action[AnyContent] = Action { implicit request =>
    val formValidationResult = GuessForm.makeGuessForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.hangman(formWithErrors))
    }, { result =>
      Hangman.makeMove(result.guess)
      Redirect(routes.HangmanController.hangman())
    })
  }
}
