package ex

import org.junit.Assert.{ assertEquals, assertFalse, assertNotEquals, assertTrue }
import org.junit.Test
import polyglot.a01b.{ LogicsImpl, Position }
import util.Optionals.*
import util.Optionals.Optional.*
import util.Sequences.*
import util.Sequences.Sequence.*
import util.Streams.Stream.iterate

class MineSweeperTest:
  private val size = 5
  private val mines = 5
  private val totalFreeCell = size * size // - mines
  private val logic: LogicsImpl = LogicsImpl(size, mines)
  private val generateRow = iterate(0)(_ + 1).take(size).toList
  private val gridPositions = generateRow.map(x => generateRow.map(y => Position(x, y))).flatMap(s => s)

  @Test def findCellInGrid(): Unit =
    val cell = logic.findCell(0, 0)
    assertNotEquals(Empty, cell)

  @Test def findCellNotInGrid(): Unit =
    val cell = logic.findCell(size, size)
    assertEquals(Empty(), cell)

  @Test def cellInBounds(): Unit =
    assertTrue(logic.checkBounds(0, 0))

  @Test def cellOutBounds(): Unit =
    assertFalse(logic.checkBounds(size, size))

  @Test def setMineInPosition(): Unit =
    logic.setMine(0, 0)
    logic.findCell(0, 0).ifPresent(cell => assertTrue(cell.isMine))

  @Test def takeRandomFreeCell(): Unit =
    gridPositions.foreach(_ => {
      val randomCell = logic.takeFreeRandomCell()
      randomCell.ifPresent(cell => assertFalse(cell.isMine))
      randomCell.ifPresent(_.isMine = true)
    })
    assertEquals(Empty(), logic.takeFreeRandomCell())

  @Test def setRandomMine(): Unit =
    gridPositions.foreach(_ => assertTrue(logic.setRandomMine()))
    assertFalse(logic.setRandomMine())

  @Test def aroundCells(): Unit =
    val aroundCells = logic.aroundCells(1, 1)
    val positions = Sequence(Position(0, 0), Position(0, 1), Position(0, 2),
                             Position(1, 0), Position(1, 2),
                             Position(2, 0), Position(2, 1), Position(2, 2))
    assertEquals(8, aroundCells.count())
    assertEquals(positions, aroundCells.map(_.position))

  @Test def aroundCellsOutGrid(): Unit =
    val aroundCells = logic.aroundCells(-1, -1)
    assertEquals(Nil(), aroundCells)

  @Test def countMinesAround(): Unit =
    val position = Position(2, 2)
    gridPositions.remove(position).foreach(position => logic.setMine(position.x, position.y))
    val freeCell = logic.takeFreeRandomCell()
    assertTrue(freeCell.isPresent)
    assertEquals(8, logic.countMinesAround(position.x, position.y))

  @Test def hitCell(): Unit =
    gridPositions.skip(1).foreach(_ => logic.setRandomMine())
    val freeCell = logic.takeFreeRandomCell()
    freeCell.ifPresent(cell => logic.hit(cell.position.x, cell.position.y))
    assertTrue(freeCell.isPresent)
    freeCell.ifPresent(cell => assertTrue(cell.isShow))

  @Test def won(): Unit =
    val position = Position(2, 2)
    gridPositions.remove(position).foreach(position => logic.setMine(position.x, position.y))
    logic.hit(position.x, position.y)
    assertTrue(logic.won)

  @Test def lost(): Unit =
    val position = Position(2, 2)
    gridPositions.remove(position).skip(1).foreach(position => logic.setMine(position.x, position.y))
    logic.hit(position.x, position.y)
    assertFalse(logic.won)