package typeclasses

import base.MatchingSuite
import org.scalatest.{Assertion, AsyncFunSuite}
import instances.indexedseq.foldableIndexSeq
import instances.int.intAddMonoid
import instances.indexedseq.chunkIndexedSeq

import scala.concurrent.Future

class ParallelFoldMapSuite extends AsyncFunSuite with MatchingSuite {

  val emptySeq = IndexedSeq.empty[String]
  val seq1 = IndexedSeq("val", "val2", "val", "val")

  def parallelFoldMapWithAssertion(seq: IndexedSeq[String])(f: String => Int)
                                  (assertion: Either[List[Throwable], Int] => Assertion): Future[Assertion] = {
    val sut = foldableIndexSeq

    val result = sut.parallelFoldMap(seq)(f)
    result.map(errorsOrValue => assertion(errorsOrValue))
  }


  test("call with empty foldable should eventually return monoid identity 0") {
    parallelFoldMapWithAssertion(emptySeq)(_.length)(_ should ===(Right(intAddMonoid.identity)))
  }

  test("call with mapping function should eventually return expected result") {
    parallelFoldMapWithAssertion(seq1)(_.length + 1)(_ should ===(Right(17)))
  }

  test("should eventually accumulate errors across cores") {
    def isStringLengthEven(s: String): Boolean = s.length % 2 == 0

    parallelFoldMapWithAssertion(seq1)(x =>
      if (isStringLengthEven(x)) x.length else x.length / 0)(_.left.get.collect {
      case ex: ArithmeticException => ex
    }.size should ===(3))
  }
}