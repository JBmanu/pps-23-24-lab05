package ex

import org.junit.Assert.{ assertEquals, assertFalse, assertNotNull, assertThrows, assertTrue }
import org.junit.Test
import util.Optionals.Optional.Just
import util.Sequences.Sequence
import util.Sequences.Sequence.*

class WarehouseTest:
  val itemCode = 3
  val item = Item(itemCode, "a")
  val warehouse = Warehouse()

  @Test def canCreateItem(): Unit =
    assertNotNull(item)

  @Test def canCreateWarehouse(): Unit =
    assertNotNull(warehouse)

  @Test def storeItem(): Unit =
    warehouse.store(item)

  @Test def containsItem(): Unit =
    warehouse.store(item)
    assertTrue(warehouse.contains(itemCode))

  @Test def retrieveItem(): Unit =
    warehouse.store(item)
    val itemOptional = warehouse.retrieve(itemCode)
    assertEquals(Just(item), itemOptional)

  @Test def removeItem(): Unit =
    warehouse.store(item)
    warehouse.remove(item)
    assertFalse(warehouse.contains(itemCode))

  @Test def searchItems(): Unit =
    val itemTagCode = 4
    val tag = "tag"
    val itemWithTag = Item(itemTagCode, "b", Sequence(tag))
    warehouse.store(itemWithTag)
    assertEquals(Sequence(itemWithTag), warehouse.searchItems(tag))

  @Test def storeItemTwoTime(): Unit =
    warehouse.store(item)
    warehouse.store(item)
    warehouse.remove(item)
    assertFalse(warehouse.contains(itemCode))

  @Test def storeNullItem(): Unit =
    warehouse.store(null)

  @Test def sameTag(): Unit =
    val itemA = Item(0, "a", Sequence("a"))
    val itemB = Item(1, "b", Sequence("a", "b"))
    val itemC = Item(2, "c", Sequence("b"))
    warehouse.store(itemA)
    warehouse.store(itemB)
    warehouse.store(itemC)
    assertEquals(Sequence(itemB, itemA), warehouse.extractSameTag("a"))
