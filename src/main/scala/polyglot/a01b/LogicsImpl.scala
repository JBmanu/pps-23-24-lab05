package polyglot.a01b

import polyglot.OptionToOptional
import polyglot.a01b.Cell.Cell
import polyglot.a01b.Position.Position
import util.Optionals.Optional as ScalaOptional
import util.Optionals.Optional.*
import util.Sequences.Sequence
import util.Sequences.Sequence.*
import util.Streams.*
import util.Streams.Stream.iterate

import scala.util.Random

object Position:
  case class Position(x: Int, y: Int)
  def apply(x: Int, y: Int): Position = Position(x, y)

object Cell:
  case class Cell(position: Position, var isMine: Boolean, var isShow: Boolean)
  def apply(x: Int, y: Int, isMine: Boolean = false, isShow: Boolean = false): Cell = Cell(Position(x, y), isMine, isShow)

/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */
class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:
  private val generateRow = iterate(0)(_ + 1).take(size).toList
  private val cells: Sequence[Cell] = generateRow.map(x => generateRow.map(y => Cell(x, y))).flatMap(s => s)
  iterate(0)(_ + 1).take(mines).toList.foreach(_ => setRandomMine())

  def findCell(x: Int, y: Int): ScalaOptional[Cell] = cells.find(cell => cell.position.equals(Position(x, y)))
  def checkBounds(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < size && y < size
  def setMine(x: Int, y: Int): Unit = findCell(x, y).ifPresent(_.isMine = true)

  def takeFreeRandomCell(): ScalaOptional[Cell] =
    val randomX = Random().between(0, size)
    val randomY = Random().between(0, size)
    val randomCell = findCell(randomX, randomY)
    randomCell match
      case _ if cells.filter(!_.isMine).isEmpty => Empty()
      case Just(cell) if cell.isMine => takeFreeRandomCell()
      case _             => randomCell

  def setRandomMine(): Unit = takeFreeRandomCell().ifPresent(cell => cell.isMine = true)

  def aroundCells(x: Int, y: Int): Sequence[Cell] =
    if checkBounds(x, y) then
      val generateX = iterate(x - 1)(_ + 1).take(3).toList
      val generateY = iterate(y - 1)(_ + 1).take(3).toList
      generateX.map(row => generateY.filter(col => checkBounds(row, col))
                                    .filter(col => row != x || col != y)
                                    .map(col => findCell(row, col)))
               .flatMap(l => l)
               .filter(_.isPresent)
               .map(_.get)
    else
      Nil()

  def countMinesAround(x: Int, y: Int): Int =
    val cellAround = aroundCells(x, y)
    val countMines = cellAround.filter(_.isMine).count()
    if (countMines == 0) {
      cellAround.filter(!_.isMine)
                .filter(!_.isShow)
                .foreach(cell => hit(cell.position.x, cell.position.y))
    }
    countMines

  def hit(x: Int, y: Int): java.util.Optional[Integer] =
    findCell(x, y) match
      case Just(cell) if !cell.isMine =>
        cell.isShow = true
        OptionToOptional(ScalaOptional.Just(countMinesAround(x, y)))
      case _                          => OptionToOptional(ScalaOptional.Empty())

  def won: Boolean = cells.filter(cell => !cell.isMine).filter(cell => !cell.isShow).isEmpty
