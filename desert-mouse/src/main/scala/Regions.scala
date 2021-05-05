package game.region

import game.dune_map._
import game.sector.{Sector}

object Regions {
  type RegionsBySector = List[List[Territory]]

  val sector1Regions = List(
    Meridan
  , CielagoWest
  , CielagoDepression
  , CielagoNorth
  )

  val sector2Regions = List(
    Meridan
  , CielagoSouth
  , CielagoDepression
  , CielagoNorth
  )

  val sector3Regions = List(
    CielagoSouth
  , CielagoEast
  , CielagoDepression
  , CielagoNorth
  )

  val sector4Regions = List(
    CielagoEast
  , SouthMesa
  , FalseWallSouth
  , HargPass
  )
  
  val sector5Regions = List(
    SouthMesa
  , TueksSietch
  , FalseWallSouth
  , PastyMesa
  , TheMinorErg
  , HargPass
  , FalseWallEast
  )

  val sector6Regions = List(
    SouthMesa
  , PastyMesa
  , TheMinorErg
  , FalseWallEast
  )

  val sector7Regions = List(
    RedChasm
  , PastyMesa
  , TheMinorErg
  , FalseWallEast
  )

  val sector8Regions = List(
    GaraKulon
  , PastyMesa
  , ShieldWall
  , TheMinorErg
  , FalseWallEast
  )

  val sector9Regions = List(
    SihayaRidge
  , Basin
  , OldGap
  , ShieldWall
  , HoleInTheRock
  , RimWallWest
  , ImperialBasin
  , FalseWallEast
  )

  val sector10Regions = List(
    OldGap
  , Arrakeen
  , ImperialBasin
  )

  val sector11Regions = List(
    OldGap
  , Tsimpo
  , BrokenLand
  , Carthag
  , ImperialBasin
  , Arsunt
  )

  val sector12Regions = List(
    BrokenLand
  , Tsimpo
  , PlasticBasin
  , HaggaBasin
  , Arsunt
  )

  val sector13Regions = List(
    RockOutcroppings
  , PlasticBasin
  , Tsimpo
  , HaggaBasin
  )

  val sector14Regions = List(
    RockOutcroppings
  , SietchTabr
  , BlightOfTheCliff
  , PlasticBasin
  , WindPass
  )

  val sector15Regions = List(
    BlightOfTheCliff
  , FuneralPlains
  , TheGreaterFlat
  , WindPass
  )

  val sector16Regions = List(
    THE_GREAT_FLAT
  , HabbanyaErg
  , FalseWallWest
  , WindPass
  )

  val sector17Regions = List(
    HabbanyaRidgeFlat
  , HabbanyaRidgeSietch
  , HabbanyaErg
  , FalseWallWest
  , WindPass
  , WindPassNorth
  )

  val sector18Regions = List(
    HabbanyaRidgeFlat
  , FalseWallWest
  , CielagoWest
  , WindPassNorth
  )

  val duneRegionsBySector: RegionsBySector = List(
      sector1Regions 
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
    , sector18Regions 
    )
}
