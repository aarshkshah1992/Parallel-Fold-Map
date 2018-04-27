package exampleapps

import scala.util.Random
import instances.indexedseq.chunkIndexedSeq
import instances.indexedseq.foldableIndexSeq
import instances.indexedseq.sortedIndexedSequenceMergeMonoid
import syntax.foldable._
import scala.concurrent.duration._
import scala.concurrent.Await

object SortList extends App {
  val lst = IndexedSeq.fill(100)(Random.nextInt(100000))

  val fut = lst.parallelFoldMap(IndexedSeq(_))

  val errorsOrSortedLst = Await.result(fut, 2 seconds)
}