package birchmd.connectfour

sealed trait CellState {
  def isEmpty: Boolean
  def nonEmpty: Boolean = !isEmpty
  def next: CellState
}

object Empty extends CellState {
  override def isEmpty: Boolean = true
  override def next: CellState = this
  override def toString: String = " "
}
object X extends CellState {
  override def isEmpty: Boolean = false
  override def next: CellState = O
  override def toString: String = "X"
}
object O extends CellState {
  override def isEmpty: Boolean = false
  override def next: CellState = X
  override def toString: String = "O"
}
