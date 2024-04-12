package ex

import org.junit.Assert.{ assertEquals, assertFalse, assertNotEquals, assertTrue }
import org.junit.Test
import polyglot.a01b.LogicsImpl
import util.Optionals.*
import util.Optionals.Optional.*
import util.Streams.Stream.iterate

class MineSweeperTest:
  private val size = 5
  private val mines = 5
  private val logic = LogicsImpl(size, mines)

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
    val totalFreeCell = size * size
    // ricodati di mettere - mine, perche non init mines
    val gridSequence = iterate(0)(_ + 1).take(totalFreeCell).toList
    val takeCell = gridSequence.map(_ => logic.takeFreeRandomCell())
    assertEquals(totalFreeCell, takeCell.count())

