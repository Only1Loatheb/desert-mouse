package game.dune_map

import scalax.collection.Graph // or scalax.collection.mutable.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._

sealed trait Region {}
object DuneMap {
  val duneMap = {
    val nodes = List(
        OldGap
      , Basin
      , SihayaRidge
      , GaraKulon
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
      , RedChasm
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
      , PastyMesa ~ RedChasm
      , RedChasm ~ SouthMesa
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

// sand regions:
case object OldGap extends Region
case object Basin extends Region
case object SihayaRidge extends Region
case object GaraKulon extends Region
case object SouthMesa extends Region
case object CielagoEast extends Region
case object CielagoSouth extends Region
case object Meridan extends Region
case object HabbanyaRidgeFlat extends Region
case object THE_GREAT_FLAT extends Region
case object TheGreaterFlat extends Region
case object FuneralPlains extends Region
case object BlightOfTheCliff extends Region
case object RockOutcroppings extends Region
case object BrokenLand extends Region
case object ImperialBasin extends Region
case object Tsimpo extends Region
case object HoleInTheRock extends Region
case object TheMinorErg extends Region
case object RedChasm extends Region
case object CielagoNorth extends Region
case object CielagoDepression extends Region
case object CielagoWest extends Region
case object HabbanyaErg extends Region
case object WindPass extends Region
case object HargPass extends Region
case object WindPassNorth extends Region
case object HaggaBasin extends Region
case object Arsunt extends Region

// City Regions:
case object Carthag extends Region
case object Arrakeen extends Region
case object TueksSietch extends Region
case object HabanyaRidgeSietch extends Region
case object SietchTabr extends Region

// Rock Regions:
case object RimWallWest extends Region
case object ShieldWall extends Region
case object PastyMesa extends Region
case object FalseWallSouth extends Region
case object FalseWallEast extends Region
case object FalseWallWest extends Region
case object PlasticBasin extends Region

// IceRegion:
case object PolarSink extends Region