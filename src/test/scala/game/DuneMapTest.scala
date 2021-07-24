import org.scalatest.FunSuite

import game.dune_map.{DuneMap, Territory}
import game.dune_map.LabelToGetSectorOnEdgeEndConversionImplicit._
import game.regions.isTerritoryOnThisSector
import utils.TerritorySectors.aSectorOnTerritory
import game.sector.FakePolarSector

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
