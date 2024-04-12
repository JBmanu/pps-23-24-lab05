package polyglot.a01b

import polyglot.OptionToOptional
import polyglot.a01b.Cell.Cell
import polyglot.a01b.Position.Position
import util.Optionals.Optional as ScalaOptional
import util.Optionals.Optional.*
import util.Sequences.Sequence
import util.Streams.*
import util.Streams.Stream.iterate

object Position:
  def apply(x: Int = 0, y: Int = 0): Position = PositionImpl(x, y)
  trait Position:
    def x: Int
    def y: Int
  private case class PositionImpl(x: Int, y: Int) extends Position

object Cell:
  def apply(x: Int, y: Int, isMine: Boolean = false, isShow: Boolean = false): Cell =
    Cell(Position(x, y), isMine, isShow)
  case class Cell(position: Position, var isMine: Boolean, var isShow: Boolean)


/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */
class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:
  private val generateRow = iterate(0)(_ + 1).take(size)
  private val cells: Sequence[Cell] = generateRow.map(x => generateRow.map(y => Cell(x, y)))
                                                 .flatMap(s => s).toList

  findCell(0, 0).ifPresent(cell => cell.isMine = true)

  def findCell(x: Int, y: Int): ScalaOptional[Cell] = cells.find(cell => cell.position.equals(Position(x, y)))

  private def checkBounds(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < size && y < size

  private def checkMinesAround(x: Int, y: Int): Integer =
    findCell(x, y) match
      case Just(cell) =>
        cell.isShow = true
        val generateX = iterate(x - 1)(_ + 1).take(3).toList
        val generateY = iterate(y - 1)(_ + 1).take(3).toList
        val cellAround = generateX.map(row => generateY.filter(col => checkBounds(row, col))
                                                       .filter(col => row != x || col != y)
                                                       .map(col => findCell(row, col)))
                                  .flatMap(l => l)
        cellAround.filter(_.isPresent).map(_.get)
                  .filter(!_.isMine)
                  .filter(!_.isShow)
                  .foreach(cell => hit(cell.position.x, cell.position.y))
        cellAround.filter(_.isPresent).map(_.get).filter(_.isMine).count()

  def hit(x: Int, y: Int): java.util.Optional[Integer] =
    findCell(x, y) match
      case Just(cell) if !cell.isMine => OptionToOptional(ScalaOptional.Just(checkMinesAround(x, y)))
      case _                          => OptionToOptional(ScalaOptional.Empty())


  def won = cells.filter(cell => !cell.isMine).filter(cell => !cell.isShow).isEmpty

