package instances

import base.BaseSuite
import int._

class IntSuite extends BaseSuite {

  test("intAdd Monoid tests") {
    val sut = intAddMonoid

    sut.op(1, 2) should ===(3)
    sut.identity should ===(0)
  }
}