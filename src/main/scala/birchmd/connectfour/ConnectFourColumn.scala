package birchmd.connectfour

case class ConnectFourColumn(n: Int, cells: IndexedSeq[CellState]) {
  def lowestEmptyIndex: Int = n - 1 - cells.reverseIterator.indexOf(Empty)
  
  def updated(index: Int, value: CellState): ConnectFourColumn = 
    ConnectFourColumn(n, cells.updated(index, value))
  
  override def toString: String = {
    cells.mkString("\n")
  }
}
