package game.state

import game.state.dune_map._
import game.state.dune_map.Territory
import game.state.sector._

/** Region is a part of territory that lays on one sector
  */
object regions {
  type TerritoriesBySector = Map[Sector, Set[Territory]]

  val sector0Territories: Set[Territory] = Set(
    Meridan,
    CielagoWest,
    CielagoDepression,
    CielagoNorth
  )

  val sector1Territories: Set[Territory] = Set(
    Meridan,
    CielagoSouth,
    CielagoDepression,
    CielagoNorth
  )

  val sector2Territories: Set[Territory] = Set(
    CielagoSouth,
    CielagoEast,
    CielagoDepression,
    CielagoNorth
  )

  val sector3Territories: Set[Territory] = Set(
    CielagoEast,
    SouthMesa,
    FalseWallSouth,
    HargPass
  )

  val sector4Territories: Set[Territory] = Set(
    SouthMesa,
    TueksSietch,
    FalseWallSouth,
    PastyMesa,
    TheMinorErg,
    HargPass,
    FalseWallEast
  )

  val sector5Territories: Set[Territory] = Set(
    SouthMesa,
    PastyMesa,
    TheMinorErg,
    FalseWallEast
  )

  val sector6Territories: Set[Territory] = Set(
    RedChasm,
    PastyMesa,
    TheMinorErg,
    FalseWallEast
  )

  val sector7Territories: Set[Territory] = Set(
    GaraKulon,
    PastyMesa,
    ShieldWall,
    TheMinorErg,
    FalseWallEast
  )

  val sector8Territories: Set[Territory] = Set(
    SihayaRidge,
    Basin,
    OldGap,
    ShieldWall,
    HoleInTheRock,
    RimWallWest,
    ImperialBasin,
    FalseWallEast
  )

  val sector9Territories: Set[Territory] = Set(
    OldGap,
    Arrakeen,
    ImperialBasin
  )

  val sector10Territories: Set[Territory] = Set(
    OldGap,
    Tsimpo,
    BrokenLand,
    Carthag,
    ImperialBasin,
    Arsunt
  )

  val sector11Territories: Set[Territory] = Set(
    BrokenLand,
    Tsimpo,
    PlasticBasin,
    HaggaBasin,
    Arsunt
  )

  val sector12Territories: Set[Territory] = Set(
    RockOutcroppings,
    PlasticBasin,
    Tsimpo,
    HaggaBasin
  )

  val sector13Territories: Set[Territory] = Set(
    RockOutcroppings,
    SietchTabr,
    BlightOfTheCliff,
    PlasticBasin,
    WindPass
  )

  val sector14Territories: Set[Territory] = Set(
    BlightOfTheCliff,
    FuneralPlains,
    TheGreatFlat,
    WindPass
  )

  val sector15Territories: Set[Territory] = Set(
    TheGreaterFlat,
    HabbanyaErg,
    FalseWallWest,
    WindPass
  )

  val sector16Territories: Set[Territory] = Set(
    HabbanyaRidgeFlat,
    HabbanyaRidgeSietch,
    HabbanyaErg,
    FalseWallWest,
    WindPass,
    WindPassNorth
  )

  val sector17Territories: Set[Territory] = Set(
    HabbanyaRidgeFlat,
    FalseWallWest,
    CielagoWest,
    WindPassNorth
  )

  val polarTerritories: Set[Territory] = Set(PolarSink)

  val duneTerritoriesBySector: TerritoriesBySector = Map(
    Sector0 -> sector0Territories,
    Sector1 -> sector1Territories,
    Sector2 -> sector2Territories,
    Sector3 -> sector3Territories,
    Sector4 -> sector4Territories,
    Sector5 -> sector5Territories,
    Sector6 -> sector6Territories,
    Sector7 -> sector7Territories,
    Sector8 -> sector8Territories,
    Sector9 -> sector9Territories,
    Sector10 -> sector10Territories,
    Sector11 -> sector11Territories,
    Sector12 -> sector12Territories,
    Sector13 -> sector13Territories,
    Sector14 -> sector14Territories,
    Sector15 -> sector15Territories,
    Sector16 -> sector16Territories,
    Sector17 -> sector17Territories,
    FakePolarSector -> polarTerritories
  )

  def isTerritoryOnThisSector(territory: Territory, sector: Sector): Boolean = {
    duneTerritoriesBySector(sector).contains(territory)
  }

}
