package exampleapps

import syntax.foldable._
import instances.int.intAddMonoid
import instances.map.mapMergeMonoid
import instances.indexedseq._
import scala.concurrent.Await
import scala.concurrent.duration._

object CharacterOccurences extends App {
  val str = "Dancing in the rain"
  val words: IndexedSeq[String] = str.toLowerCase.split(" ").toIndexedSeq

  val fut = words.parallelFoldMap(_.groupBy(identity).mapValues(_.length))

  val errorsOrCharOccurrences = Await.result(fut, 2 seconds)
}