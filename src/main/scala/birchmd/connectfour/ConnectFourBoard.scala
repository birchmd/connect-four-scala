package birchmd.connectfour

case class ConnectFourBoard(m: Int, state: IndexedSeq[ConnectFourColumn]) {
  def move(player: CellState, column: Int): ConnectFourBoard = {
    val ind = state(column).lowestEmptyIndex
    if (ind == state(column).n) { //no empty cells, move is invalid
      this 
    } else {
      val newState = state.updated(column, state(column).updated(ind, player))
      ConnectFourBoard(m, newState)
    }
  }
  
  def cols: Iterator[IndexedSeq[CellState]] = state.iterator.map(_.cells)
  
  def rows: Iterator[IndexedSeq[CellState]] = Iterator.range(0, state.head.n).map(i => {
    Iterator.range(0, m).map(j => state(j).cells(i)).toVector
  })
  
  def diag(i: Int, j: Int, up: Boolean): IndexedSeq[CellState] = if (up) {
    val l = scala.math.min(i + 1, m - j)
    Iterator.range(0, l).map(k => state(j + k).cells(i - k)).toVector
  } else {
    val l = scala.math.min(state.head.n - i, m - j)
    Iterator.range(0, l).map(k => state(j + k).cells(i + k)).toVector
  }
  
  def diags: Iterator[IndexedSeq[CellState]] = {
    val n = state.head.n
    Iterator.range(0, n).map(i => diag(i, 0, up=true)).filter(_.length > 3) ++
      Iterator.range(1, m).map(j => diag(n - 1, j, up=true)).filter(_.length > 3) ++
        Iterator.range(0, m).map(j => diag(0, j, up=false)).filter(_.length > 3) ++
          Iterator.range(1, n).map(i => diag(i, 0, up=false)).filter(_.length > 3)
  }
  
  def fourInARow(sets: Iterator[IndexedSeq[CellState]]): Option[CellState] = {
    sets.map(set => {
      set.sliding(4).find(subset => {
        subset.length == 4 && subset.head.nonEmpty && subset.forall(_ == subset.head)
      }).map(_.head)
    }).reduce(_ orElse _)
  }
  
  def winner: Option[CellState] = {
    //need to look for 4 of the same (non-empty) cell in a row
    
    val vertWin = fourInARow(cols)
    val horizWin = fourInARow(rows)
    val diagWin = fourInARow(diags)
    
    vertWin.orElse(horizWin).orElse(diagWin)
  }
  
  def isFull: Boolean = state.forall(_.cells.forall(_.nonEmpty))
  
  override def toString: String = {
    val boarder = Vector.fill(4 * m - 3)("_").mkString
    s"$boarder\n" + 
      rows.map(_.mkString(" | ")).mkString("\n") + 
        s"\n$boarder"
  }
}
object ConnectFourBoard {
  def empty(n: Int = 6, m: Int = 7): ConnectFourBoard = {
    val state = Vector.fill(m)(ConnectFourColumn(n, Vector.fill(n)(Empty)))
    ConnectFourBoard(m, state)
  }
}
