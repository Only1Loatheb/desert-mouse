import org.scalatest.FunSuite

import game.state.dune_map.{DuneMap, Territory}
import game.state.dune_map.LabelToGetSectorOnEdgeEndConversionImplicit._
import game.state.regions.isTerritoryOnThisSector
import game.state.sector.FakePolarSector
import game.utils.TerritorySectors.aSectorOnTerritory

class DuneMapTest extends FunSuite {
  test("DuneMap.edges.label.GetSectorOnEdgeEnd.isExhaustive") {
    assert(
      DuneMap.duneMap.edges.map(edge => edge.nodes.map(edge.label(FakePolarSector, _))).nonEmpty
    )
  }

  test("DuneMap.edges.label.GetSectorOnEdgeEnd.areOnTerritory") {
    assert(
      DuneMap.duneMap.edges
        .map(edge =>
          edge.nodes.map((node: Territory) =>
            if (isTerritoryOnThisSector(node, edge.label(aSectorOnTerritory(node), node).head)) true
            else throw new IllegalStateException(s"node=[$node], edge=[$edge], sector=[${aSectorOnTerritory(node)}]")
          )
        )
        .flatten
        .reduce(_ && _)
    )
  }
}
