package games

object WordSquare {

  def generateSquare(s: String): Array[String] = {
    def recFunc(s: String, n: Int): String = n match {
      case _ if n == (s.length - 1) => s.reverse.replace("", " ")
      case 0 => s.replace("", " ") + "^" + recFunc(s, n+1)
      case _ => (s.charAt(n) + (" " * (s.length - 2)) + s.charAt(s.length - 1 - n)).replace("", " ") + "^" + recFunc(s, n+1)
    }
    recFunc(s.toUpperCase, 0).split('^')
  }
}
