package controllers

import javax.inject.Inject

import config.AppConfig
import games.hangman.Hangman
import forms.GuessForm
import models.viewModels.HangmanViewModel
import play.api.mvc.{Action, AnyContent, ControllerComponents}

class HangmanController @Inject()(cc: ControllerComponents,
                                  implicit val appConfig: AppConfig) extends FrontendController(cc) {

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
