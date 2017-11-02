package birchmd.connectfour

import scala.collection.mutable
import scala.math.{min, max}

case class ConnectFourAI(piece: CellState, maxDepth: Int = 3) extends Player {
  
  private def moves(board: ConnectFourBoard): Iterator[Int] = 
    Iterator.range(0, board.m).filter(j => board.state(j).cells.exists(_.isEmpty))
  private val opponent: CellState = piece.next

  private def toNumeric(sets: Iterator[IndexedSeq[CellState]]): Iterator[IndexedSeq[Double]] = 
    sets.filter(_.exists(_.isEmpty))
      .map(set => {
        set.map(v => v match {
          case `piece` => 1d
          case `opponent` => -1d
          case _ => 0
        })
    })
  
  def huristic(board: ConnectFourBoard): Double = {
    toNumeric(board.cols).map(_.sliding(4).map(_.sum / 4d).sum).sum + 
      toNumeric(board.rows).map(_.sliding(4).map(_.sum / 4d).sum).sum + 
        toNumeric(board.diags).map(_.sliding(4).map(_.sum / 4d).sum).sum
  }
  
  def alphabeta(board: ConnectFourBoard, 
                depth: Int = maxDepth,
                alpha: Double = Double.NegativeInfinity,
                beta: Double = Double.PositiveInfinity,
                maximizingPlayer: Boolean = false): Double = {
    val winner = board.winner
    if (winner.isDefined | board.isFull | depth == 0) {
      winner match {
        case None => huristic(board)
        case Some(w) => if (w == piece) Double.PositiveInfinity else Double.NegativeInfinity
      }
    } else {
      if (maximizingPlayer) {
        moves(board).foldLeft(Double.NegativeInfinity){
          case (v, i) =>
            val newAlpha = max(alpha, v)
            if (beta <= newAlpha) {
              v
            } else {
              val newV = max(v, alphabeta(board.move(piece, i), depth - 1, newAlpha, beta, false))
              newV
            }
        }
      } else {
        moves(board).foldLeft(Double.PositiveInfinity){
          case (v, i) =>
            val newBeta = min(beta, v)
            if (newBeta <= alpha) {
              v
            } else {
              val newV = min(v, alphabeta(board.move(opponent, i), depth - 1, alpha, newBeta, true))
              newV
            }
        }
      }
    }
  }
  
  def appraiseMove(board: ConnectFourBoard, i: Int): Double = alphabeta(board.move(piece, i))
  
  override def nextMove(board: ConnectFourBoard): Int = moves(board).maxBy(i => appraiseMove(board, i))
}
