package birchmd.connectfour.play

import birchmd.connectfour.{ConnectFourGame, Human, O, X}

object PlayHuman {
  def main(args: Array[String]): Unit = {
    ConnectFourGame.play(Human(X), Human(O))
  }
}
