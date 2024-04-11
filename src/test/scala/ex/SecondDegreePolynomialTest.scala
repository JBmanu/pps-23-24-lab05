package ex

import ex.SecondDegreePolynomial
import org.junit.Assert.{ assertEquals, assertNotNull }
import org.junit.Test

class SecondDegreePolynomialTest:
  val a = 1.0
  val b = 2.0
  val c = 3.0
  val pol: SecondDegreePolynomial = SecondDegreePolynomial(a, b, c)

  @Test def canCreate(): Unit =
    assertNotNull(pol)

  @Test def readConstant(): Unit =
    assertEquals(c, pol.constant, 0.0)

  @Test def readFirstDegree(): Unit =
    assertEquals(a, pol.firstDegree, 0.0)

  @Test def readSecondDegree(): Unit =
    assertEquals(b, pol.secondDegree, 0.0)

  @Test def sum(): Unit =
    val pol1 = SecondDegreePolynomial(1, 2, 3)
    val polRis = pol + pol1
    assertEquals(2, polRis.firstDegree, 0.0)
    assertEquals(4, polRis.secondDegree, 0.0)
    assertEquals(6, polRis.constant, 0.0)

  @Test def subtract(): Unit =
    val pol1 = SecondDegreePolynomial(1, 2, 3)
    val polRis = pol - pol1
    assertEquals(0, polRis.firstDegree, 0.0)
    assertEquals(0, polRis.secondDegree, 0.0)
    assertEquals(0, polRis.constant, 0.0)

  @Test def equals(): Unit =
    assertEquals(SecondDegreePolynomial(1, 2, 3), pol)