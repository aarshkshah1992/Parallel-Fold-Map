package exampleapps

import syntax.foldable._
import instances.int.intAddMonoid
import instances.map.mapMergeMonoid
import instances.indexedseq._
import scala.concurrent.duration._
import scala.concurrent.Await

object WordOccurences extends App {
  val str = "To be or not to be"
  val words: IndexedSeq[String] = str.toLowerCase.split(" ").toIndexedSeq

  val fut = words.parallelFoldMap(s => Map(s -> 1))

  val errorsOrWordOccurrences = Await.result(fut, 2 seconds)
}