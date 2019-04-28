package controllers

import javax.inject.Inject

import games.hangman.Hangman
import forms.GuessForm
import models.viewModels.HangmanViewModel
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import views.html.HangmanView

class HangmanController @Inject()(hangmanView: HangmanView)(
                                  implicit cc: ControllerComponents) extends FrontendController {

  def hangman: Action[AnyContent] = Action { implicit request =>
    val model = HangmanViewModel(Hangman.newGame, None)
    Ok(hangmanView(model))
  }

  def makeGuess(model: HangmanViewModel): Action[AnyContent] = Action { implicit request =>
    val formValidationResult = GuessForm.makeGuessForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(hangmanView(model, formWithErrors))
    }, { result =>
      val newModel = Hangman.makeMove(model.gameState, result.guess)
      Ok(hangmanView(newModel))
    })
  }
}
