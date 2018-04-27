package instances

import base.BaseSuite
import indexedseq._

class IndexSeqSuite extends BaseSuite {
  val emptyIndexedSeq = IndexedSeq.empty[Int]
  val seq1 = IndexedSeq(7, 12, 19)
  val seq2 = IndexedSeq(1, 7, 20)

  test("sortedIndexedSeqMerge Monoid tests") {
    val sut = sortedIndexedSequenceMergeMonoid[Int]

    sut.identity should ===(emptyIndexedSeq)
    sut.op(emptyIndexedSeq, emptyIndexedSeq) should ===(emptyIndexedSeq)
    sut.op(emptyIndexedSeq, seq1) should ===(seq1)
    sut.op(seq1, seq2) should ===((seq1 ++ seq2).sorted)
  }

  test("chunkable IndexedSeq tests") {
    val sut = chunkIndexedSeq

    sut.getChunks(3, emptyIndexedSeq) should ===(List(emptyIndexedSeq))
    sut.getChunks(1, seq1) should ===(List(seq1))
    sut.getChunks(2, seq2) should ===(List(IndexedSeq(1, 7), IndexedSeq(20)))
  }

  test("foldable IndexedSeq tests") {
    val sut = foldableIndexSeq

    sut.foldLeft(emptyIndexedSeq)(1)(_ + _) should ===(1)
    sut.foldRight(emptyIndexedSeq)(2)(_ + _) should ===(2)
    sut.foldLeft(seq1)(emptyIndexedSeq)((x, y) => y +: x) should ===(seq1.reverse)
    sut.foldRight(seq2)(emptyIndexedSeq)(_ +: _) should ===(seq2)
  }
}