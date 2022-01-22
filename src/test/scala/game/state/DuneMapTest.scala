package game.state

import org.scalatest.flatspec.AnyFlatSpec

import game.state.dune_map.duneMap
import game.state.regions.isTerritoryOnThisSector
import utils.TerritorySectors.sectorsOnTerritory

class DuneMapTest extends AnyFlatSpec {

  // "DuneMap.edges.label.GetSectorOnEdgeEnd.isExhaustive" should "" in{

  //   // https://stackoverflow.com/questions/34534002/getting-subclasses-of-a-sealed-trait
  //   val mapNodeType = ru.typeOf[Territory]
  //   val mapNodeClass = mapNodeType.typeSymbol.asClass    
  //   assert(mapNodeClass.isTrait) 
  //   assert(mapNodeClass.isSealed) 
  //   assert(mapNodeClass.knownDirectSubclasses.size == duneMap.graph.keySet.size) 

  //   assert(
  //     duneMap.graph.keySet.forall { node =>
  //       duneMap
  //         .getEdgeLabelsFrom(node)
  //         .forall { edge => edge._2(FakePolarSector).nonEmpty}
  //     }
  //   )
  // }

  "DuneMap.edges.label.GetSectorOnEdgeEnd.areOnTerritory" should "" in{
    assert(
      duneMap.graph.keySet.forall { nodeFrom =>
        duneMap.getEdgeLabelsFrom(nodeFrom).forall { case edge @ (nodeTo, getSectorOnEdgeEnd) =>
          sectorsOnTerritory(nodeFrom).forall { sectorFrom =>
            getSectorOnEdgeEnd(sectorFrom).forall { foundSector =>
              if (isTerritoryOnThisSector(nodeTo, foundSector)) true
              else throw new IllegalStateException(s"nodeFrom => ${nodeFrom} nodeTo => ${edge._1} sectorFrom => ${sectorFrom} foundSector => ${foundSector}")
            }
          }
        }
      }
    )
  }
}
