package typeclasses

trait Chunkable[F[_]] {
  def getChunks[A](nChunks: Int, xs: F[A]): List[F[A]]
}

object Chunkable {
  def apply[F[_]: Chunkable] = implicitly[Chunkable[F]]
}