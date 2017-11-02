package birchmd.connectfour.play

import birchmd.connectfour.{ConnectFourAI, ConnectFourGame, Human, O, X}

object PlayAI {
  def main(args: Array[String]): Unit = {
    
    println("Would you like to be X or O?")
    val humanPiece = scala.io.Source.stdin.getLines().next() match {
      case "X" => X
      case _ => O
    }
    println("Choose AI difficulty (1 - 5):")
    val depth = scala.io.Source.stdin.getLines().next().toInt
    
    val ai = ConnectFourAI(humanPiece.next, depth)
    
    ConnectFourGame.play(Human(humanPiece), ai)
  }
}
