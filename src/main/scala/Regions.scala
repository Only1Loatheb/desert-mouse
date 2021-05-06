package game.region

import game.dune_map._
import game.dune_map.DuneMap.Territory
import game.sector.{Sector}

object Regions {
  type RegionsBySector = List[List[Territory]]

  val sector0Regions = List(
    Meridan
  , CielagoWest
  , CielagoDepression
  , CielagoNorth
  )

  val sector1Regions = List(
    Meridan
  , CielagoSouth
  , CielagoDepression
  , CielagoNorth
  )

  val sector2Regions = List(
    CielagoSouth
  , CielagoEast
  , CielagoDepression
  , CielagoNorth
  )

  val sector3Regions = List(
    CielagoEast
  , SouthMesa
  , FalseWallSouth
  , HargPass
  )
  
  val sector4Regions = List(
    SouthMesa
  , TueksSietch
  , FalseWallSouth
  , PastyMesa
  , TheMinorErg
  , HargPass
  , FalseWallEast
  )

  val sector5Regions = List(
    SouthMesa
  , PastyMesa
  , TheMinorErg
  , FalseWallEast
  )

  val sector6Regions = List(
    RedChasm
  , PastyMesa
  , TheMinorErg
  , FalseWallEast
  )

  val sector7Regions = List(
    GaraKulon
  , PastyMesa
  , ShieldWall
  , TheMinorErg
  , FalseWallEast
  )

  val sector8Regions = List(
    SihayaRidge
  , Basin
  , OldGap
  , ShieldWall
  , HoleInTheRock
  , RimWallWest
  , ImperialBasin
  , FalseWallEast
  )

  val sector9Regions = List(
    OldGap
  , Arrakeen
  , ImperialBasin
  )

  val sector10Regions = List(
    OldGap
  , Tsimpo
  , BrokenLand
  , Carthag
  , ImperialBasin
  , Arsunt
  )

  val sector11Regions = List(
    BrokenLand
  , Tsimpo
  , PlasticBasin
  , HaggaBasin
  , Arsunt
  )

  val sector12Regions = List(
    RockOutcroppings
  , PlasticBasin
  , Tsimpo
  , HaggaBasin
  )

  val sector13Regions = List(
    RockOutcroppings
  , SietchTabr
  , BlightOfTheCliff
  , PlasticBasin
  , WindPass
  )

  val sector14Regions = List(
    BlightOfTheCliff
  , FuneralPlains
  , TheGreaterFlat
  , WindPass
  )

  val sector15Regions = List(
    THE_GREAT_FLAT
  , HabbanyaErg
  , FalseWallWest
  , WindPass
  )

  val sector16Regions = List(
    HabbanyaRidgeFlat
  , HabbanyaRidgeSietch
  , HabbanyaErg
  , FalseWallWest
  , WindPass
  , WindPassNorth
  )

  val sector17Regions = List(
    HabbanyaRidgeFlat
  , FalseWallWest
  , CielagoWest
  , WindPassNorth
  )

  val duneRegionsBySector: RegionsBySector = List(
      sector0Regions 
    , sector1Regions 
    , sector2Regions 
    , sector3Regions 
    , sector4Regions 
    , sector5Regions 
    , sector6Regions 
    , sector7Regions 
    , sector8Regions 
    , sector9Regions 
    , sector10Regions 
    , sector11Regions 
    , sector12Regions 
    , sector13Regions 
    , sector14Regions 
    , sector15Regions 
    , sector16Regions 
    , sector17Regions 
    )
}
