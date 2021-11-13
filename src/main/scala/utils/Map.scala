package game.utils

object map {

  implicit class MapImprovements[K, V](val thisMap: Map[K, V]) {

    def sumWith(plus: (V, V) => V)(otherMap: Map[K, V]): Map[K, V] = {
      otherMap.foldLeft(thisMap){
        case (acc, (k, v)) => acc.updatedWith(k)(_.map(plus(_, v)).orElse(Some(v)))
      }
    }
  }

  implicit class MapOfSetImprovements[K, V](val thisMap: Map[K, Set[V]]) {

    def sumValueSet(otherMap: Map[K, Set[V]]): Map[K, Set[V]] = {
      thisMap.sumWith(_ ++ _)(otherMap)
    }
  }

}
