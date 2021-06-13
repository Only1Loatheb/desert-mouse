package game.region

import game.dune_map._
import game.dune_map.DuneMap.Territory
import game.sector.{Sector}
/**
  * Region is a part of territory that lays on one sector 
  */
object Regions {
  type TerritoriesBySector = List[List[Territory]]

  def isTerritoryOnThisSector(territory: Territory, sector: Sector): Boolean = {
    duneTerritoriesBySector(sector.number).contains(territory)
  }

  val sector0Territories = List(
    Meridan
  , CielagoWest
  , CielagoDepression
  , CielagoNorth
  )

  val sector1Territories = List(
    Meridan
  , CielagoSouth
  , CielagoDepression
  , CielagoNorth
  )

  val sector2Territories = List(
    CielagoSouth
  , CielagoEast
  , CielagoDepression
  , CielagoNorth
  )

  val sector3Territories = List(
    CielagoEast
  , SouthMesa
  , FalseWallSouth
  , HargPass
  )
  
  val sector4Territories = List(
    SouthMesa
  , TueksSietch
  , FalseWallSouth
  , PastyMesa
  , TheMinorErg
  , HargPass
  , FalseWallEast
  )

  val sector5Territories = List(
    SouthMesa
  , PastyMesa
  , TheMinorErg
  , FalseWallEast
  )

  val sector6Territories = List(
    RedChasm
  , PastyMesa
  , TheMinorErg
  , FalseWallEast
  )

  val sector7Territories = List(
    GaraKulon
  , PastyMesa
  , ShieldWall
  , TheMinorErg
  , FalseWallEast
  )

  val sector8Territories = List(
    SihayaRidge
  , Basin
  , OldGap
  , ShieldWall
  , HoleInTheRock
  , RimWallWest
  , ImperialBasin
  , FalseWallEast
  )

  val sector9Territories = List(
    OldGap
  , Arrakeen
  , ImperialBasin
  )

  val sector10Territories = List(
    OldGap
  , Tsimpo
  , BrokenLand
  , Carthag
  , ImperialBasin
  , Arsunt
  )

  val sector11Territories = List(
    BrokenLand
  , Tsimpo
  , PlasticBasin
  , HaggaBasin
  , Arsunt
  )

  val sector12Territories = List(
    RockOutcroppings
  , PlasticBasin
  , Tsimpo
  , HaggaBasin
  )

  val sector13Territories = List(
    RockOutcroppings
  , SietchTabr
  , BlightOfTheCliff
  , PlasticBasin
  , WindPass
  )

  val sector14Territories = List(
    BlightOfTheCliff
  , FuneralPlains
  , THE_GREAT_FLAT
  , WindPass
  )

  val sector15Territories = List(
    TheGreaterFlat
  , HabbanyaErg
  , FalseWallWest
  , WindPass
  )

  val sector16Territories = List(
    HabbanyaRidgeFlat
  , HabbanyaRidgeSietch
  , HabbanyaErg
  , FalseWallWest
  , WindPass
  , WindPassNorth
  )

  val sector17Territories = List(
    HabbanyaRidgeFlat
  , FalseWallWest
  , CielagoWest
  , WindPassNorth
  )

  val polarTerritories = List(PolarSink)

  val duneTerritoriesBySector: TerritoriesBySector = List(
      sector0Territories 
    , sector1Territories 
    , sector2Territories 
    , sector3Territories 
    , sector4Territories 
    , sector5Territories 
    , sector6Territories 
    , sector7Territories 
    , sector8Territories 
    , sector9Territories 
    , sector10Territories 
    , sector11Territories 
    , sector12Territories 
    , sector13Territories 
    , sector14Territories 
    , sector15Territories 
    , sector16Territories 
    , sector17Territories
    , polarTerritories 
    )
}
