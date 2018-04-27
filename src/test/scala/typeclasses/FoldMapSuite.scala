package typeclasses

import base.BaseSuite
import instances.indexedseq.foldableIndexSeq
import instances.int.intAddMonoid

class FoldMapSuite extends BaseSuite {

  val emptyIndexedSeq = IndexedSeq.empty[Int]
  val seq1 = IndexedSeq(7, 12, 19)

  test("foldMap tests") {
    val sut = foldableIndexSeq

    sut.foldMap(emptyIndexedSeq)(_ + 1) should ===(intAddMonoid.identity)
    sut.foldMap(seq1)(identity) should ===(seq1.sum)
    sut.foldMap(seq1)(_ * 2) should ===(seq1.sum * 2)
  }
}