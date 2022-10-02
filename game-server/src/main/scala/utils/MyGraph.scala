package utils

object my_graph {

  /** There is always edge in both directions. Edge label (value on the edge) can be different
    * depending on the direction. All values of type NODE are present as a node. Is immutable. Will
    * work in scala 3 Uses map but could use array and enum index in scala 3. I dont want to bother
    * with enumeratum now.
    */
  final case class MyGraph[NODE, EDGE_LABEL](graph: Map[NODE, Map[NODE, EDGE_LABEL]]) {

    def areNeighbors(x: NODE, y: NODE): Boolean = {
      graph(x).contains(y)
    }

    def getEdgeLabelsFrom(x: NODE): Set[(NODE, EDGE_LABEL)] = {
      graph(x).toSet
    }

    val getNodes: Set[NODE] = {
      graph.keySet
    }
  }


  final case class Edge[NODE, EDGE_LABEL](
      n1: NODE,
      n2: NODE,
      fromN1toN2: EDGE_LABEL,
      fromN2toN1: EDGE_LABEL
  )
  // in scala 3
  // https://alvinalexander.com/source-code/scala-3-enum-how-loop-iterate-over-elements-values/
  object MyGraph {

    /**
     * Graph has to be connected (no disconnected supgraphs).
     * @param edges has to contain every possible node of type NODE (must be exhaustive) 
     * @return bidirectional graph 
     * generated with github copilot
     */
    def apply[NODE, EDGE_LABEL](edges: List[Edge[NODE, EDGE_LABEL]]): MyGraph[NODE, EDGE_LABEL] = {
      val graph = edges.foldLeft(Map.empty[NODE, Map[NODE, EDGE_LABEL]])((acc, edge) => {
        acc + 
        (edge.n1 -> (acc.getOrElse(edge.n1, Map.empty[NODE, EDGE_LABEL]) + (edge.n2 -> edge.fromN1toN2))) +
        (edge.n2 -> (acc.getOrElse(edge.n2, Map.empty[NODE, EDGE_LABEL]) + (edge.n1 -> edge.fromN2toN1)))
      })
      MyGraph(graph)
    }
  }

}
