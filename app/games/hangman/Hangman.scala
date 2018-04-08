package games.hangman

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Random

object Hangman {

  val hangmanImage = Vector(
    "  _______ ",
    "  |     | ",
    "  |     O ",
    "  |    /|\\",
    "  |    / \\",
    "  |       ",
    "__|___    ",
    "|    |    "
  )
  val wordList: Vector[String] = Source.fromFile("app/games/hangman/wordlist.txt").getLines().toVector
  val rand = new Random()

  var guessWord = ""
  var remainingChars = ""
  var currentStatus = new ArrayBuffer[Char]
  var prevGuesses = new ArrayBuffer[Char]
  var turnCounter = 0
  var failCounter = 0
  var messageOutput = ArrayBuffer("Please enter a letter to start the game.")
  var hangingOutput = new ArrayBuffer[String]
  var gameStarted = false
  var gameFinished = false

  def newGame(): Unit = {
    guessWord = wordList(rand.nextInt(wordList.length))
    remainingChars = guessWord
    currentStatus = new ArrayBuffer[Char]
    for (_ <- 1 to guessWord.length) currentStatus += '_'
    prevGuesses = new ArrayBuffer[Char]
    turnCounter = 0
    failCounter = 0
    messageOutput = ArrayBuffer("Please enter a letter to start the game.")
    hangingOutput = new ArrayBuffer[String]
    gameStarted = true
    gameFinished = false
  }

  def makeMove(guess: Char): Unit = {
    if(!gameFinished) {
      messageOutput.clear
      messageOutput = ArrayBuffer(s"Turn number: ${turnCounter + 1}", s"Remaining incorrect guesses: ${7 - failCounter}")
      prevGuesses += guess
      messageOutput += s"Previous guesses: ${prevGuesses.mkString(" ")}"
      if (remainingChars.contains(guess)) {
        messageOutput += "Correct guess."
        for (i <- 0 until guessWord.length) if (remainingChars(i) == guess) currentStatus(i) = guess
        messageOutput += s"${currentStatus.mkString(" ")}"
        remainingChars = remainingChars.replaceAll(guess.toString, " ")
        if (!currentStatus.mkString.contains("_")) {
          messageOutput = gameEnd(victory = true, guessWord)
          gameFinished = true
        }
        turnCounter += 1
      }
      else {
        messageOutput += "Incorrect guess."
        messageOutput += s"${currentStatus.mkString(" ")}"
        hangingOutput += hangmanImage(failCounter)
        failCounter += 1
        if (failCounter > 7) {
          messageOutput = gameEnd(victory = false, guessWord)
          gameFinished = true
        }
        turnCounter += 1
      }
    } else {
      newGame()
      makeMove(guess)
    }
  }

  def gameEnd(victory: Boolean, answer: String): ArrayBuffer[String] = {
    val message = if(victory) "You win! Yes dude!" else "Game over! YOU DIED!"
    ArrayBuffer(message, s"The word was $answer.", "Enter a letter to start a new game.")
  }
}
