package instances

import typeclasses.Monoid

object int {

  implicit val intAddMonoid = new Monoid[Int] {
    override def op(x: Int, y: Int): Int = x + y

    override def identity: Int = 0
  }
}
