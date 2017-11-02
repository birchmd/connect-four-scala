package birchmd.connectfour

object ConnectFourGame {
  def play(p1: Player, p2: Player): Unit = {
    import scala.collection.mutable.ArrayBuffer
    
    val turnStates: ArrayBuffer[ConnectFourBoard] = new ArrayBuffer[ConnectFourBoard]()
    turnStates += ConnectFourBoard.empty()
    val ncols = turnStates.last.m
    
    var activePiece: CellState = O
    while (!turnStates.last.winner.isDefined && !turnStates.last.isFull) {
      activePiece = activePiece.next
      val activePlayer = if (p1.piece == activePiece) p1 else p2
      println(turnStates.last)
      val col = activePlayer.nextMove(turnStates.last)
      println(s"Player $activePiece adds piece to column ${col + 1}")
      turnStates += turnStates.last.move(activePiece, col)
    }
    
    println(turnStates.last)
    println(turnStates.last.winner.map(p => s"$p wins!").getOrElse("Draw!"))
  }
}
