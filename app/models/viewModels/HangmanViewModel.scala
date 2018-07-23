package models.viewModels

import games.hangman.HangmanGameState

case class HangmanViewModel(gameState: HangmanGameState, gameWin: Option[Boolean])