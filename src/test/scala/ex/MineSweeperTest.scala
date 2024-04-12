package ex

import org.junit.Assert.{ assertEquals, assertNotEquals }
import org.junit.Test
import polyglot.a01b.LogicsImpl
import util.Optionals.*
import util.Optionals.Optional.*

class MineSweeperTest:
  val logic = LogicsImpl(5, 5)

  @Test def findCell(): Unit =
    val cell = logic.findCell(0, 0)
    assertNotEquals(Empty, cell)
