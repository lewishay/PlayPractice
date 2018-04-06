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

  def getWord: String = wordList(rand.nextInt(wordList.length))

  var guessWord = ""
  var current = new ArrayBuffer[Char]
  var prevGuesses = new ArrayBuffer[Char]
  var savedGuessWord = ""
  var playing = false
  var counter = 0
  var failCounter = 0
  var result: ArrayBuffer[String] = ArrayBuffer("Please enter a letter to start the game.")

  def newGame(): Unit = {
    guessWord = wordList(rand.nextInt(wordList.length))
    current = new ArrayBuffer[Char]
    for (z <- 1 to guessWord.length) current += '_'
    savedGuessWord = guessWord
    playing = true
    counter = 0
    failCounter = 0
    prevGuesses = new ArrayBuffer[Char]
  }

  def makeMove(guess: Char): Any = {
    if(playing) {
      var resultArray = new ArrayBuffer[String]
      resultArray += s"Turn number: ${counter + 1}. Remaining incorrect guesses: ${7 - failCounter}\n"
      prevGuesses += guess
      resultArray += s"Previous guesses: ${prevGuesses.mkString(" ")}"
      if(guessWord.contains(guess)) {
        resultArray += "Correct guess."
        for(i <- 0 until guessWord.length) {
          if(guessWord(i) == guess) current(i) = guess
        }
        resultArray += s"${current.mkString(" ")}"
        guessWord = guessWord.replaceAll(guess.toString, " ")
        if(!current.mkString.contains("_")) {
          resultArray += "You win! Yes dude!"
          resultArray += s"The word was $savedGuessWord"
          resultArray += "Enter a letter to start a new game!"
          playing = false
        }
        counter += 1
      }
      else {
        resultArray += "Incorrect guess."
        resultArray += s"${current.mkString(" ")}"
        for (i <- 0 to failCounter) resultArray += s"${hangmanImage(i)}"
        failCounter += 1
        if (failCounter > 7) {
          resultArray += "Game over! YOU DIED!"
          resultArray += s"The word was $savedGuessWord"
          resultArray += "Enter a letter to start a new game!"
          playing = false
        }
        counter += 1
      }
      result = resultArray
    }
    else {
      "You must start a new game first!"
    }
  }
}
