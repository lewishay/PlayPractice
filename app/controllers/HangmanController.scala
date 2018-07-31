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

  def makeGuess(model: HangmanViewModel): Action[AnyContent] = Action { implicit request =>
    val formValidationResult = GuessForm.makeGuessForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      BadRequest(views.html.hangman(model, formWithErrors))
    }, { result =>
      val newModel = Hangman.makeMove(model.gameState, result.guess)
      Ok(views.html.hangman(newModel))
    })
  }
}
