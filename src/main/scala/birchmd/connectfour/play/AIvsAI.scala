package birchmd.connectfour.play

import birchmd.connectfour.{ConnectFourAI, ConnectFourGame, O, X}

object AIvsAI {
  def main(args: Array[String]): Unit = {
    val depth: Int = 5
    ConnectFourGame.play(ConnectFourAI(X, depth), ConnectFourAI(O, depth))
  }
}