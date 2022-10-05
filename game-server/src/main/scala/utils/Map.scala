package utils

import cats.implicits._

object map {

  implicit class MapImprovements[K, V](private val thisMap: Map[K, V]) {

    def unionWith(plus: (V, V) => V)(otherMap: Map[K, V]): Map[K, V] = {
      otherMap.foldLeft(thisMap) { case (acc, (key, value)) =>
        acc.updatedWith(key)(_.fold(value)(plus(_, value)).some)
      }
    }

    def diffWith(minus: (V, V) => V)(otherMap: Map[K, V]): Map[K, V] = {
      otherMap.foldLeft(thisMap) { case (acc, (key, value)) =>
        acc
          .updatedWith(key)(
            _.fold(throw new IllegalArgumentException)(minus(_, value).some),
          )
      }
    }
  }

  implicit class MapOfSetImprovements[K, V](private val thisMap: Map[K, Set[V]]) {

    def unionValueSets(otherMap: Map[K, Set[V]]): Map[K, Set[V]] = {
      thisMap.unionWith(_ ++ _)(otherMap)
    }

    def diffValueSets(otherMap: Map[K, Set[V]]): Map[K, Set[V]] = {
      thisMap.unionWith(_ diff _)(otherMap)
    }
  }

}
