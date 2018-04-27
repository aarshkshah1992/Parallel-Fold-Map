package syntax

import typeclasses.{Chunkable, Foldable, Monoid}

import scala.concurrent.Future

object foldable {

  implicit class FoldableOps[F[_] : Chunkable : Foldable, A](fa: F[A]) {
    def parallelFoldMap[B: Monoid](func: A => B): Future[Either[List[Throwable], B]] =
      Foldable[F].parallelFoldMap(fa)(func)
  }

}