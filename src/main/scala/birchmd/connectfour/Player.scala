package birchmd.connectfour

trait Player {
  val piece: CellState
  def nextMove(board: ConnectFourBoard): Int
}
