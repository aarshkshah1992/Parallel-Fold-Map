package instances

import typeclasses.Monoid

object map {

  implicit def mapMergeMonoid[K, V: Monoid] = new Monoid[Map[K, V]] {
    override def op(x: Map[K, V], y: Map[K, V]): Map[K, V] =
      (x.keySet ++ y.keySet).foldLeft(identity) { (acc, key) =>
        acc.updated(key, Monoid[V].op(x.getOrElse(key, Monoid[V].identity),
          y.getOrElse(key, Monoid[V].identity)))
      }

    override def identity: Map[K, V] = Map.empty[K, V]
  }
}