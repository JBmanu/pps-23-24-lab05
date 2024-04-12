package util

import org.junit.*
import org.junit.Assert.*
import util.Streams.Stream
import util.Sequences.Sequence
class StreamTest:
  @Test
  def testTake: Unit =
    val s = Stream.iterate(1)(_ + 1)
    val s1 = s.take(5)
    assertEquals(s1.toList, Sequence(1, 2, 3, 4, 5))

  @Test
  def testConcat: Unit =
    val s1 = Stream.iterate(1)(_ + 1).take(5)
    val s2 = Stream.iterate(6)(_ + 1).take(5)
    val s3 = s1.concat(s2)
    assertEquals(s3.toList, Sequence(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

  @Test
  def testFlatMap: Unit =
    val s = Stream.iterate(1)(_ + 1)
    val s1 = s.flatMap(x => Stream(x, x + 1))
    assertEquals(s1.take(5).toList, Sequence(1, 2, 2, 3, 3))
  @Test
  def testMap: Unit =
    val s = Stream.iterate(1)(_ + 1)
    val s1 = s.map(_ * 2)
    assertEquals(s1.take(5).toList, Sequence(2, 4, 6, 8, 10))

  @Test
  def testFilter: Unit =
    val s = Stream.iterate(1)(_ + 1)
    val s1 = s.filter(_ % 2 == 0)
    assertEquals(s1.take(5).toList, Sequence(2, 4, 6, 8, 10))
    
  




