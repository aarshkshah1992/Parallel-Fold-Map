package instances

import typeclasses.{Chunkable, Foldable, Monoid}

import scala.annotation.tailrec

object indexedseq {

  implicit def indexedSequenceMergeMonoid[T] = new Monoid[IndexedSeq[T]] {
    override def op(x: IndexedSeq[T], y: IndexedSeq[T]): IndexedSeq[T] = x ++ y

    override def identity: IndexedSeq[T] = IndexedSeq.empty[T]
  }

  implicit def sortedIndexedSequenceMergeMonoid[T: Ordering] =
    new Monoid[IndexedSeq[T]] {
      override def op(x: IndexedSeq[T], y: IndexedSeq[T]): IndexedSeq[T] = {

        @tailrec
        def merge(xs: IndexedSeq[T], ys: IndexedSeq[T], acc: IndexedSeq[T]): IndexedSeq[T] =
          (xs, ys) match {
            case (IndexedSeq(), _) => acc ++ ys
            case (_, IndexedSeq()) => acc ++ xs
            case (x1 +: xs1, y1 +: ys1) => if (Ordering[T].lteq(x1, y1))
              merge(xs1, ys, acc :+ x1) else {
              merge(xs, ys1, acc :+ y1)
            }
          }

        merge(x, y, identity)
      }

      override def identity: IndexedSeq[T] = IndexedSeq.empty[T]
    }

  implicit val chunkIndexedSeq = new Chunkable[IndexedSeq] {
    override def getChunks[A](nChunks: Int, xs: IndexedSeq[A]): List[IndexedSeq[A]] = {
      if (xs.isEmpty) List(xs)
      else {
        val groupSize = (1.0 * xs.size / nChunks).ceil.toInt
        xs.grouped(groupSize).toList
      }
    }
  }

  implicit val foldableIndexSeq = new Foldable[IndexedSeq] {
    override def foldLeft[A, B](fa: IndexedSeq[A])(z: B)(f: (B, A) => B): B =
      fa.foldLeft(z)(f)

    override def foldRight[A, B](fa: IndexedSeq[A])(z: B)(f: (A, B) => B): B =
      fa.foldRight(z)(f)
  }
}