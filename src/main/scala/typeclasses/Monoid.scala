package typeclasses

trait Monoid[T] {
  def op(x: T, y: T): T

  def identity: T
}

object Monoid {
  def apply[T: Monoid] = implicitly[Monoid[T]]
}
