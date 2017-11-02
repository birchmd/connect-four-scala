package birchmd.connectfour

case class Human(piece: CellState) extends Player {
  override def nextMove(board: ConnectFourBoard): Int = {
    println(s"Player $piece, select a column (1 - ${board.m}):")
    //use 1 to 7 numbering for player convenience
    val col = scala.io.Source.stdin.getLines().next().toInt - 1
    col
  }
}