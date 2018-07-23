package controllers

import javax.inject.Inject

import games.hangman.Hangman
import forms.GuessForm
import models.viewModels.HangmanViewModel
import play.api.i18n.I18nSupport
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class HangmanController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  def hangman: Action[AnyContent] = Action { implicit request =>
    val model = HangmanViewModel(Hangman.newGame, None)
    Ok(views.html.hangman(model))
  }

//  Some(HangmanGameState(
//    "hey", 4, 5, Vector('h', 'e', 'y'), Vector('_', '_', 'n'), Vector('o'), Vector("  _______ ", "  |     | ")
//  ))

  def makeGuess: Action[AnyContent] = Action { implicit request =>
    val formValidationResult = GuessForm.makeGuessForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.hangman(formWithErrors))
    }, { result =>
      Hangman.makeMove(gameState, result.guess)
      Redirect(routes.HangmanController.hangman())
    })
  }
}
