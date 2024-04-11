package ex

import org.junit.Assert.{ assertEquals, assertFalse, assertNotNull, assertThrows, assertTrue }
import org.junit.Test
import util.Optionals.Optional.Just
import util.Sequences.Sequence.*

class WarehouseTest:
  val itemCode = 3
  val item = Item(itemCode, "a")
  val itemTagCode = 4
  val tag = "tag"
  val itemWithTag = Item(itemTagCode, "b", Cons(tag, Nil()))
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
    warehouse.store(itemWithTag)
    assertEquals(Cons(itemWithTag, Nil()), warehouse.searchItems(tag))

  @Test def storeItemTwoTime(): Unit =
    warehouse.store(item)
    assertThrows(classOf[IllegalArgumentException], () => warehouse.store(item))

  @Test def storeNullItem(): Unit =
    assertThrows(classOf[IllegalArgumentException], () => warehouse.store(null))
