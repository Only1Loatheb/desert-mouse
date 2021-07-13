import org.scalatest.FunSuite

import game.dune_map.DuneMap
import game.region.Regions.isTerritoryOnThisSector
import utils.TerritorySectors.aSectorsOnTerritory
import DuneMap.LabelToGetSectorOnEdgeEndConversionImplicit._
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
          edge.nodes.map((node: DuneMap.Territory) =>
            if (isTerritoryOnThisSector(node, edge.label(aSectorsOnTerritory(node), node).head)) true
            else throw new IllegalStateException(s"node=[$node], edge=[$edge], sector=[${aSectorsOnTerritory(node)}]")
          )
        )
        .flatten
        .reduce(_ && _)
    )
  }
}
