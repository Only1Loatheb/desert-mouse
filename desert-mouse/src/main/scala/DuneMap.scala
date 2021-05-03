package dune_map

import scalax.collection.Graph // or scalax.collection.mutable.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._

object DuneMap {
  val duneMap = {
    val nodes = List(
        OldGap
      , Basin
      , SihayaRidge
      , GaraKulon
      , RedChasm
      , SouthMesa
      , CielagoEast
      , CielagoSouth
      , Meridan
      , HabbanyaRidgeFlat
      , THE_GREAT_FLAT
      , TheGreaterFlat
      , FuneralPlains
      , BlightOfTheCliff
      , RockOutcroppings
      , BrokenLand
      , ImperialBasin
      , Tsimpo
      , HoleInTheRock
      , TheMinorErg
      , RedChasma
      , CielagoNorth
      , CielagoDepression
      , CielagoWest
      , HabbanyaErg
      , WindPass
      , HargPass
      , WindPassNorth
      , HaggaBasin
      , Arsunt
      , Carthag
      , Arrakeen
      , TueksSietch
      , HabanyaRidgeSietch
      , SietchTabr
      , RimWallWest
      , ShieldWall
      , PastyMesa
      , FalseWallSouth
      , FalseWallEast
      , FalseWallWest
      , PlasticBasin
      , PolarSink
      )
    val edges = List(
        OldGap ~ BrokenLand
      , OldGap ~ Tsimpo
      , OldGap ~ ImperialBasin
      , OldGap ~ Arrakeen
      , OldGap ~ RimWallWest
      , OldGap ~ Basin
      , Basin ~ RimWallWest
      , Basin ~ HoleInTheRock
      , Basin ~ SihayaRidge
      , SihayaRidge ~ HoleInTheRock
      , SihayaRidge ~ ShieldWall
      , SihayaRidge ~ GaraKulon
      , GaraKulon ~ ShieldWall
      , GaraKulon ~ PastyMesa
      , PastyMesa ~ ShieldWall
      , PastyMesa ~ TheMinorErg
      , PastyMesa ~ FalseWallSouth
      , PastyMesa ~ TueksSietch
      , PastyMesa ~ SouthMesa
      , PastyMesa ~ RedChasma
      , RedChasma ~ SouthMesa
      , SouthMesa ~ TueksSietch
      , SouthMesa ~ FalseWallSouth
      , SouthMesa ~ CielagoEast
      , CielagoEast ~ FalseWallSouth
      , CielagoEast ~ CielagoNorth
      , CielagoEast ~ CielagoDepression
      , CielagoEast ~ CielagoSouth
      , CielagoSouth ~ CielagoDepression
      , CielagoSouth ~ Meridan
      , Meridan ~ CielagoDepression
      , Meridan ~ CielagoWest
      , Meridan ~ HabbanyaRidgeFlat
      , HabbanyaRidgeFlat ~ CielagoWest
      , HabbanyaRidgeFlat ~ FalseWallWest
      , HabbanyaRidgeFlat ~ HabbanyaErg
      , HabbanyaRidgeFlat ~ HabanyaRidgeSietch
      , HabbanyaErg ~ FalseWallWest
      , HabbanyaErg ~ TheGreaterFlat
      , TheGreaterFlat ~ FalseWallWest
      , TheGreaterFlat ~ WindPass
      , TheGreaterFlat ~ THE_GREAT_FLAT
      , THE_GREAT_FLAT ~ WindPass
      , THE_GREAT_FLAT ~ PlasticBasin
      , THE_GREAT_FLAT ~ FuneralPlains
      , FuneralPlains ~ PlasticBasin
      , FuneralPlains ~ BlightOfTheCliff
      , BlightOfTheCliff ~ PlasticBasin
      , BlightOfTheCliff ~ SietchTabr
      , BlightOfTheCliff ~ RockOutcroppings
      , RockOutcroppings ~ SietchTabr
      , RockOutcroppings ~ PlasticBasin
      , RockOutcroppings ~ BrokenLand
      , BrokenLand ~ PlasticBasin
      , BrokenLand ~ Tsimpo
      , Arrakeen ~ ImperialBasin
      , Arrakeen ~ RimWallWest
      , RimWallWest ~ ImperialBasin
      , RimWallWest ~ HoleInTheRock
      , HoleInTheRock ~ ImperialBasin
      , HoleInTheRock ~ ShieldWall
      , ShieldWall ~ ImperialBasin
      , ShieldWall ~ FalseWallEast
      , ShieldWall ~ TheMinorErg
      , TheMinorErg ~ FalseWallEast
      , TheMinorErg ~ HargPass
      , TheMinorErg ~ FalseWallSouth
      , FalseWallSouth ~ HargPass
      , FalseWallSouth ~ CielagoNorth
      , FalseWallSouth ~ TueksSietch
      , CielagoNorth ~ HargPass
      , CielagoNorth ~ PolarSink
      , CielagoNorth ~ WindPassNorth
      , CielagoNorth ~ CielagoWest
      , CielagoNorth ~ CielagoDepression
      , CielagoDepression ~ CielagoWest
      , CielagoWest ~ WindPassNorth
      , CielagoWest ~ WindPass
      , CielagoWest ~ FalseWallWest
      , FalseWallWest ~ WindPass
      , WindPass ~ CielagoWest
      , WindPass ~ WindPassNorth
      , WindPass ~ PolarSink
      , WindPass ~ HaggaBasin
      , WindPass ~ PlasticBasin
      , PlasticBasin ~ HaggaBasin
      , PlasticBasin ~ Tsimpo
      , PlasticBasin ~ SietchTabr
      , Tsimpo ~ HaggaBasin
      , Tsimpo ~ Carthag
      , Tsimpo ~ ImperialBasin
      , ImperialBasin ~ FalseWallEast
      , ImperialBasin ~ PolarSink
      , ImperialBasin ~ Arsunt
      , ImperialBasin ~ Carthag
      , FalseWallEast ~ PolarSink
      , FalseWallEast ~ HargPass
      , HargPass ~ PolarSink
      , WindPassNorth ~ PolarSink
      , HaggaBasin ~ PolarSink
      , HaggaBasin ~ Arsunt
      , Arsunt ~ PolarSink
      , Arsunt ~ Carthag
    )
    Graph.from(nodes, edges)
  }
}

trait Region {
}
// sand regions:
case class OldGap() extends Region
case class Basin() extends Region
case class SihayaRidge() extends Region
case class GaraKulon() extends Region
case class RedChasm() extends Region
case class SouthMesa() extends Region
case class CielagoEast() extends Region
case class CielagoSouth() extends Region
case class Meridan() extends Region
case class HabbanyaRidgeFlat() extends Region
case class THE_GREAT_FLAT() extends Region
case class TheGreaterFlat() extends Region
case class FuneralPlains() extends Region
case class BlightOfTheCliff() extends Region
case class RockOutcroppings() extends Region
case class BrokenLand() extends Region
case class ImperialBasin() extends Region
case class Tsimpo() extends Region
case class HoleInTheRock() extends Region
case class TheMinorErg() extends Region
case class RedChasma() extends Region
case class CielagoNorth() extends Region
case class CielagoDepression() extends Region
case class CielagoWest() extends Region
case class HabbanyaErg() extends Region
case class WindPass() extends Region
case class HargPass() extends Region
case class WindPassNorth() extends Region
case class HaggaBasin() extends Region
case class Arsunt() extends Region

// City Regions:
case class Carthag() extends Region
case class Arrakeen() extends Region
case class TueksSietch() extends Region
case class HabanyaRidgeSietch() extends Region
case class SietchTabr() extends Region

// Rock Regions:
case class RimWallWest() extends Region
case class ShieldWall() extends Region
case class PastyMesa() extends Region
case class FalseWallSouth() extends Region
case class FalseWallEast() extends Region
case class FalseWallWest() extends Region
case class PlasticBasin() extends Region

// IceRegion:
case class PolarSink() extends Region