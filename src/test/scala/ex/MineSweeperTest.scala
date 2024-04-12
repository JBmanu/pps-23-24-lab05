package ex

import org.junit.Test
import polyglot.a01b.LogicsImpl

class MineSweeperTest:
  
  @Test def canCreate(): Unit =
    val logic = LogicsImpl(5, 5)
    