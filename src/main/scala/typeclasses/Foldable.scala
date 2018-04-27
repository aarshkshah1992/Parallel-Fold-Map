package typeclasses

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

trait Foldable[F[_]] {
  type ErrorsOrValue[B] = Either[List[Throwable], B]

  def foldLeft[A, B](fa: F[A])(z: B)(f: (B, A) => B): B

  def foldRight[A, B](fa: F[A])(z: B)(f: (A, B) => B): B

  final def foldMap[A, B: Monoid](fa: F[A])(func: A => B): B =
    foldLeft(fa)(Monoid[B].identity)((b, a) => Monoid[B].op(b, func(a)))

  final def parallelFoldMap[A, B: Monoid]
  (fa: F[A])(func: A => B)(implicit chunkable: Chunkable[F]): Future[ErrorsOrValue[B]] = {

    def accumulateErrorsOrSuccess(futures: Future[List[Try[B]]]) = {
      val successfulFuts = futures.map(_.collect { case Success(b) => b })
      val failedFuts = futures.map(_.collect { case Failure(ex) => ex })

      successfulFuts.zipWith(failedFuts)((values, errors) => if (errors.isEmpty)
        Right(values.fold(Monoid[B].identity)(Monoid[B].op)) else
        Left(errors)
      )
    }

    val numCores = Runtime.getRuntime.availableProcessors

    val chunks = chunkable.getChunks(numCores, fa)

    val futures = Future.traverse(chunks)(chunk =>
      Future(foldMap(chunk)(func))
        .map(Success(_)).recover {
        case ex => Failure(ex)
      })

    accumulateErrorsOrSuccess(futures)
  }

}

object Foldable {
  def apply[T[_] : Foldable] = implicitly[Foldable[T]]
}