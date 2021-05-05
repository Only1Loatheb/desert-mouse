package game.dune_map

import scalax.collection.Graph // or scalax.collection.mutable.Graph
import scalax.collection.GraphPredef._, scalax.collection.GraphEdge._
import game.sector._

sealed trait Territory{}
sealed trait Sand{}
sealed trait Rock{}
sealed trait Ice{}
sealed trait City{}

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
      , HabbanyaRidgeSietch
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
      , HabbanyaRidgeFlat ~ HabbanyaRidgeSietch
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

// sand Territorys:
case object OldGap extends Territory with Sand
case object Basin extends Territory with Sand
case object SihayaRidge extends Territory with Sand
case object GaraKulon extends Territory with Sand
case object SouthMesa extends Territory with Sand
case object CielagoEast extends Territory with Sand
case object CielagoSouth extends Territory with Sand
case object Meridan extends Territory with Sand
case object HabbanyaRidgeFlat extends Territory with Sand
case object THE_GREAT_FLAT extends Territory with Sand
case object TheGreaterFlat extends Territory with Sand
case object FuneralPlains extends Territory with Sand
case object BlightOfTheCliff extends Territory with Sand
case object RockOutcroppings extends Territory with Sand
case object BrokenLand extends Territory with Sand
case object ImperialBasin extends Territory with Sand
case object Tsimpo extends Territory with Sand
case object HoleInTheRock extends Territory with Sand
case object TheMinorErg extends Territory with Sand
case object RedChasm extends Territory with Sand
case object CielagoNorth extends Territory with Sand
case object CielagoDepression extends Territory with Sand
case object CielagoWest extends Territory with Sand
case object HabbanyaErg extends Territory with Sand
case object WindPass extends Territory with Sand
case object HargPass extends Territory with Sand
case object WindPassNorth extends Territory with Sand
case object HaggaBasin extends Territory with Sand
case object Arsunt extends Territory with Sand

// City Territorys:
case object Carthag extends Territory with City
case object Arrakeen extends Territory with City
case object TueksSietch extends Territory with City
case object HabbanyaRidgeSietch extends Territory with City
case object SietchTabr extends Territory with City

// Rock Territorys:
case object RimWallWest extends Territory with Rock
case object ShieldWall extends Territory with Rock
case object PastyMesa extends Territory with Rock
case object FalseWallSouth extends Territory with Rock
case object FalseWallEast extends Territory with Rock
case object FalseWallWest extends Territory with Rock
case object PlasticBasin extends Territory with Rock

// IceTerritory:
case object PolarSink extends Territory with Ice