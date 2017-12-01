package games

object WordSquare {

  def generateSquare(s: String): Array[String] = {
    if(s.length > 20) {
      "That word is too long!".split('^')
    }
    else {
      def recFunc(s: String, n: Int): String = n match {
        case _ if n == (s.length - 1) => s.map(x => x + " ").mkString.reverse.tail
        case 0 => s.map(x => x + " ").mkString.substring(0, (s.length * 2) - 1) + "^" + recFunc(s, n+1)
        case _ =>
          (s.charAt(n) + (" " * (s.length - 2)) + s.charAt(s.length - 1 - n)).map(x => x + " ").mkString.substring(0, (s.length * 2) - 1) + "^" + recFunc(s, n+1)
      }
      recFunc(s.toUpperCase.replace(" ", " "), 0).split('^')
    }
  }
}
