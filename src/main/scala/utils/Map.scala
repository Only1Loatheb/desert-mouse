package utils

object map {

  implicit class MapImprovements[K, V](val thisMap: Map[K, V]) {

    def unionWith(plus: (V, V) => V)(otherMap: Map[K, V]): Map[K, V] = {
      otherMap.foldLeft(thisMap){ case (acc, (key, value)) =>
        acc
          .updatedWith(key)(_.map(plus(_, value))
          .orElse(Some(value)))
      }
    }

    def diffWith(minus: (V, V) => V)(otherMap: Map[K, V]): Map[K, V] = {
      otherMap.foldLeft(thisMap){ case (acc, (key, value)) =>
        acc
          .updatedWith(key)(_.map(minus(_, value))
          .orElse(throw new IllegalArgumentException))
      }
    }
  }

  implicit class MapOfSetImprovements[K, V](val thisMap: Map[K, Set[V]]) {

    def unionValueSets(otherMap: Map[K, Set[V]]): Map[K, Set[V]] = {
      thisMap.unionWith(_ ++ _)(otherMap)
    }

    def diffValueSets(otherMap: Map[K, Set[V]]): Map[K, Set[V]] = {
      thisMap.unionWith(_ diff _)(otherMap)
    }
  }

}
