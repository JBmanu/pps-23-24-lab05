package ex

import org.junit.Assert.{ assertEquals, assertFalse, assertNotEquals, assertThrows, assertTrue }
import org.junit.Test
import polyglot.a01b.{ LogicsImpl, Position }
import util.Optionals.*
import util.Optionals.Optional.*
import util.Sequences.{ Sequence, * }
import util.Sequences.Sequence.*
import util.Streams.Stream.iterate

class MineSweeperTest:
  private val size = 5
  private val mines = 5
  private val totalFreeCell = size * size // - mines
  private val logic = LogicsImpl(size, mines)
  private val sizeGridSequence = iterate(0)(_ + 1).take(totalFreeCell).toList


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

  @Test def takeRandomFreeCell(): Unit =
    val freeCells = sizeGridSequence.map(_ => logic.takeFreeRandomCell())
    freeCells.foreach(opt => assertTrue(!opt.get.isMine))

  @Test def setRandomMine(): Unit =
    sizeGridSequence.foreach(_ => logic.setRandomMine())
    assertFalse(logic.setRandomMine())

  @Test def aroundCells(): Unit =
    val aroundCells = logic.aroundCells(1, 1)
    val positions = Cons(Position(0, 0),
                         Cons(Position(0, 1),
                              Cons(Position(0, 2),
                                   Cons(Position(1, 0),
                                        Cons(Position(1, 2),
                                             Cons(Position(2, 0),
                                                  Cons(Position(2, 1),
                                                       Cons(Position(2, 2), Nil()))))))))
    assertEquals(8, aroundCells.count())
    assertEquals(positions, aroundCells.map(_.position))
  