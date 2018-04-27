package instances

import base.BaseSuite
import map.mapMergeMonoid
import int.intAddMonoid

class MapSuite extends BaseSuite {

  val emptyMap = Map.empty[String, Int]
  val map1 = Map("test" -> 1, "best" -> 2)
  val map2 = Map("test" -> 2, "rest" -> 3)
  val map3 = Map("test" -> 3, "rest" -> 3, "best" -> 2)

  test("mapMerge Monoid test") {
    val sut = mapMergeMonoid[String, Int]

    sut.identity should ===(emptyMap)
    sut.op(emptyMap, emptyMap) should ===(emptyMap)
    sut.op(map1, emptyMap) should ===(map1)
    sut.op(map1, map2) should ===(map3)
  }
}