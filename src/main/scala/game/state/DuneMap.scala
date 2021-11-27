package game.state

import game.state.sector._
import utils.my_graph._

object dune_map {

  sealed trait Territory extends Product with Serializable
  sealed trait Sand
  sealed trait Rock
  sealed trait Ice
  sealed trait Stronghold
  sealed trait HasOrnithopters
  
  type DuneMapEdge = Edge[Territory, GetSectorOnEdgeEnd]
  def DuneMapEdge: (Territory, Territory,  GetSectorOnEdgeEnd, GetSectorOnEdgeEnd) => DuneMapEdge = {
    Edge[Territory, GetSectorOnEdgeEnd]
  }
  type GetSectorOnEdgeEnd = Sector => Set[Sector]
  type DuneMapGraph = MyGraph[Territory, GetSectorOnEdgeEnd]


  val duneMap: DuneMapGraph = {
    val edges: List[DuneMapEdge] = List(
        DuneMapEdge(OldGap, BrokenLand, _ => Set(Sector10), _ => Set(Sector10))
      , DuneMapEdge(OldGap, Tsimpo, _ => Set(Sector10), _ => Set(Sector10))
      , DuneMapEdge(OldGap, ImperialBasin, _ => Set(Sector9), _ => Set(Sector9))
      , DuneMapEdge(OldGap, Arrakeen, _ => Set(Sector9), _ => Set(Sector9)) 
      , DuneMapEdge(OldGap, RimWallWest, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(OldGap, Basin, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(Basin, RimWallWest, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(Basin, HoleInTheRock, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(Basin, SihayaRidge, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(SihayaRidge, HoleInTheRock, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(SihayaRidge, ShieldWall, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(SihayaRidge, GaraKulon, _ => Set(Sector7), _ => Set(Sector8))
      , DuneMapEdge(GaraKulon, ShieldWall, _ => Set(Sector7), _ => Set(Sector7))
      , DuneMapEdge(GaraKulon, PastyMesa, _ => Set(Sector7), _ => Set(Sector7))
      , DuneMapEdge(PastyMesa, ShieldWall, _ => Set(Sector7), _ => Set(Sector7))
      , DuneMapEdge(PastyMesa, TheMinorErg, x => Set(x), x => Set(x))
      , DuneMapEdge(PastyMesa, FalseWallSouth, _ => Set(Sector4), _ => Set(Sector4))
      , DuneMapEdge(PastyMesa, TueksSietch, _ => Set(Sector4), _ => Set(Sector4))
      , DuneMapEdge(PastyMesa, SouthMesa, _ => Set(Sector5), _ => Set(Sector5))
      , DuneMapEdge(PastyMesa, RedChasm, _ => Set(Sector6), _ => Set(Sector6))
      , DuneMapEdge(RedChasm, SouthMesa, _ => Set(Sector5), _ => Set(Sector6))
      , DuneMapEdge(SouthMesa, TueksSietch, _ => Set(Sector4), _ => Set(Sector4))
      , DuneMapEdge(SouthMesa, FalseWallSouth, _ => Set(Sector3), _ => Set(Sector3))
      , DuneMapEdge(SouthMesa, CielagoEast, _ => Set(Sector3), _ => Set(Sector3))
      , DuneMapEdge(CielagoEast, FalseWallSouth, _ => Set(Sector3), _ => Set(Sector3))
      , DuneMapEdge(CielagoEast, CielagoNorth, _ => Set(Sector2), _ => Set(Sector2))
      , DuneMapEdge(CielagoEast, CielagoDepression, _ => Set(Sector2), _ => Set(Sector2))
      , DuneMapEdge(CielagoEast, CielagoSouth, _ => Set(Sector2), _ => Set(Sector2))
      , DuneMapEdge(CielagoSouth, CielagoDepression, x => Set(x), { case Sector0 => Set(Sector1); case prevSector => Set(prevSector)})
      , DuneMapEdge(CielagoSouth, Meridan, _ => Set(Sector1), _ => Set(Sector1))
      , DuneMapEdge(Meridan, CielagoDepression, x => Set(x), { case Sector2 => Set(Sector1); case prevSector => Set(prevSector)})
      , DuneMapEdge(Meridan, CielagoWest, _ => Set(Sector0), _ => Set(Sector0))
      , DuneMapEdge(Meridan, HabbanyaRidgeFlat, _ => Set(Sector17), _ => Set(Sector0))
      , DuneMapEdge(HabbanyaRidgeFlat, CielagoWest, _ => Set(Sector17), _ => Set(Sector17))
      , DuneMapEdge(HabbanyaRidgeFlat, FalseWallWest, x => Set(x), { case Sector15 => Set(Sector16); case prevSector => Set(prevSector)})
      , DuneMapEdge(HabbanyaRidgeFlat, HabbanyaErg, _ => Set(Sector16), _ => Set(Sector16))
      , DuneMapEdge(HabbanyaRidgeFlat, HabbanyaRidgeSietch, _ => Set(Sector16), _ => Set(Sector16))
      , DuneMapEdge(HabbanyaErg, FalseWallWest, _ => Set(Sector16), _ => Set(Sector16))
      , DuneMapEdge(HabbanyaErg, TheGreaterFlat, _ => Set(Sector15), _ => Set(Sector15))
      , DuneMapEdge(TheGreaterFlat, FalseWallWest, _ => Set(Sector15), _ => Set(Sector15))
      , DuneMapEdge(TheGreaterFlat, WindPass, _ => Set(Sector15), _ => Set(Sector15))
      , DuneMapEdge(TheGreaterFlat, TheGreatFlat, _ => Set(Sector14), _ => Set(Sector15))
      , DuneMapEdge(TheGreatFlat, WindPass, _ => Set(Sector14), _ => Set(Sector14))
      , DuneMapEdge(TheGreatFlat, PlasticBasin, _ => Set(Sector13), _ => Set(Sector14))
      , DuneMapEdge(TheGreatFlat, FuneralPlains, _ => Set(Sector14), _ => Set(Sector14))
      , DuneMapEdge(FuneralPlains, PlasticBasin, _ => Set(Sector13), _=> Set(Sector14))
      , DuneMapEdge(FuneralPlains, BlightOfTheCliff, _ => Set(Sector14), _ => Set(Sector14))
      , DuneMapEdge(BlightOfTheCliff, PlasticBasin, _ => Set(Sector13), _ => Set(Sector13))
      , DuneMapEdge(BlightOfTheCliff, SietchTabr, _ => Set(Sector13), _ => Set(Sector13))
      , DuneMapEdge(BlightOfTheCliff, RockOutcroppings, _ => Set(Sector13), _ => Set(Sector13))
      , DuneMapEdge(RockOutcroppings, SietchTabr, _ => Set(Sector13), _ => Set(Sector13))
      , DuneMapEdge(RockOutcroppings, PlasticBasin, _ => Set(Sector12), _ => Set(Sector12)) // ???
      , DuneMapEdge(RockOutcroppings, BrokenLand, _ => Set(Sector11), _ => Set(Sector12))
      , DuneMapEdge(BrokenLand, PlasticBasin, _ => Set(Sector11), _ => Set(Sector11))
      , DuneMapEdge(BrokenLand, Tsimpo, x => Set(x), { case Sector12 => Set(Sector11); case prevSector => Set(prevSector)})
      , DuneMapEdge(Arrakeen, ImperialBasin, _ => Set(Sector9), _ => Set(Sector9))
      , DuneMapEdge(Arrakeen, RimWallWest, _ => Set(Sector8), _ => Set(Sector9))
      , DuneMapEdge(RimWallWest, ImperialBasin, _ => Set(Sector9), _ => Set(Sector8))
      , DuneMapEdge(RimWallWest, HoleInTheRock, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(HoleInTheRock, ImperialBasin, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(HoleInTheRock, ShieldWall, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(ShieldWall, ImperialBasin, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(ShieldWall, FalseWallEast, x => Set(x), _ => Set(Sector7, Sector8))
      , DuneMapEdge(ShieldWall, TheMinorErg, _ => Set(Sector7), _ => Set(Sector7))
      , DuneMapEdge(TheMinorErg, FalseWallEast, x => Set(x), { case Sector8 => Set(Sector7); case prevSector => Set(prevSector)})
      , DuneMapEdge(TheMinorErg, HargPass, _ => Set(Sector4), _ => Set(Sector4))
      , DuneMapEdge(TheMinorErg, FalseWallSouth, _ => Set(Sector4), _ => Set(Sector4))
      , DuneMapEdge(FalseWallSouth, HargPass, x => Set(x), x => Set(x))
      , DuneMapEdge(FalseWallSouth, CielagoNorth,  _ => Set(Sector2), _ => Set(Sector3))
      , DuneMapEdge(FalseWallSouth, TueksSietch, _ => Set(Sector4), _ => Set(Sector4))
      , DuneMapEdge(CielagoNorth, HargPass, _ => Set(Sector3), _ => Set(Sector2))
      , DuneMapEdge(CielagoNorth, PolarSink, _ => Set(FakePolarSector), _ => Set(Sector0, Sector1, Sector2))
      , DuneMapEdge(CielagoNorth, WindPassNorth, _ => Set(Sector17), _ => Set(Sector0))
      , DuneMapEdge(CielagoNorth, CielagoWest, _ => Set(Sector0), _ => Set(Sector0))
      , DuneMapEdge(CielagoNorth, CielagoDepression, x => Set(x), x => Set(x))
      , DuneMapEdge(CielagoDepression, CielagoWest, _ => Set(Sector0), _ => Set(Sector0))
      , DuneMapEdge(CielagoWest, WindPassNorth, _ => Set(Sector17), _ => Set(Sector0))
      , DuneMapEdge(CielagoWest, WindPass, _ => Set(Sector16), _ => Set(Sector17))
      , DuneMapEdge(CielagoWest, FalseWallWest, _ => Set(Sector17), _ => Set(Sector17))
      , DuneMapEdge(FalseWallWest, WindPass, _ => Set(Sector15, Sector16), _ => Set(Sector15, Sector16))
      , DuneMapEdge(WindPass, CielagoWest, _ => Set(Sector17), _ => Set(Sector16))
      , DuneMapEdge(WindPass, WindPassNorth, _ => Set(Sector16), _ => Set(Sector16))
      , DuneMapEdge(WindPass, PolarSink, _ => Set(FakePolarSector), _ => Set(Sector13, Sector14, Sector15))
      , DuneMapEdge(WindPass, HaggaBasin, _ => Set(Sector12), _ => Set(Sector13))
      , DuneMapEdge(WindPass, PlasticBasin, _ => Set(Sector13), _ => Set(Sector13))
      , DuneMapEdge(PlasticBasin, HaggaBasin, _ => Set(Sector12), _ => Set(Sector12))
      , DuneMapEdge(PlasticBasin, Tsimpo, _ => Set(Sector11, Sector12), _ => Set(Sector11, Sector12))
      , DuneMapEdge(PlasticBasin, SietchTabr, _ => Set(Sector13), _ => Set(Sector13))
      , DuneMapEdge(Tsimpo, HaggaBasin, _ => Set(Sector11, Sector12), x => Set(x))
      , DuneMapEdge(Tsimpo, Carthag, _ => Set(Sector10), _ => Set(Sector10))
      , DuneMapEdge(Tsimpo, ImperialBasin, _ => Set(Sector10), _ => Set(Sector10))
      , DuneMapEdge(ImperialBasin, FalseWallEast, _ => Set(Sector8), _ => Set(Sector8))
      , DuneMapEdge(ImperialBasin, PolarSink,  _ => Set(FakePolarSector), _ => Set(Sector9))
      , DuneMapEdge(ImperialBasin, Arsunt, _ => Set(Sector10), _ => Set(Sector10))
      , DuneMapEdge(ImperialBasin, Carthag, _ => Set(Sector10), _ => Set(Sector10))
      , DuneMapEdge(FalseWallEast, PolarSink, _ => Set(FakePolarSector), _ => Set(Sector4, Sector5, Sector6, Sector7, Sector8))
      , DuneMapEdge(FalseWallEast, HargPass, _ => Set(Sector4), _ => Set(Sector4))
      , DuneMapEdge(HargPass, PolarSink, _ => Set(FakePolarSector), _ => Set(Sector3))
      , DuneMapEdge(WindPassNorth, PolarSink, _ => Set(FakePolarSector), _ => Set(Sector16, Sector17))
      , DuneMapEdge(HaggaBasin, PolarSink, _ => Set(FakePolarSector), _ => Set(Sector12))
      , DuneMapEdge(HaggaBasin, Arsunt, _ => Set(Sector11), _ => Set(Sector11))
      , DuneMapEdge(Arsunt, PolarSink, _ => Set(FakePolarSector), _ => Set(Sector10, Sector11))
      , DuneMapEdge(Arsunt, Carthag, _ => Set(Sector10), _ => Set(Sector10))
    )
    MyGraph(edges)
  }

  case object Meridan extends Territory with Sand
  case object CielagoSouth extends Territory with Sand
  case object CielagoEast extends Territory with Sand
  case object SouthMesa extends Territory with Sand
  case object RedChasm extends Territory with Sand
  case object GaraKulon extends Territory with Sand
  case object SihayaRidge extends Territory with Sand
  case object Basin extends Territory with Sand
  case object OldGap extends Territory with Sand
  case object Arsunt extends Territory with Sand
  case object HaggaBasin extends Territory with Sand
  case object WindPassNorth extends Territory with Sand
  case object HargPass extends Territory with Sand
  case object WindPass extends Territory with Sand
  case object CielagoWest extends Territory with Sand
  case object CielagoDepression extends Territory with Sand
  case object CielagoNorth extends Territory with Sand
  case object TheMinorErg extends Territory with Sand
  case object HoleInTheRock extends Territory with Sand
  case object Tsimpo extends Territory with Sand
  case object ImperialBasin extends Territory with Sand
  case object BrokenLand extends Territory with Sand
  case object RockOutcroppings extends Territory with Sand
  case object BlightOfTheCliff extends Territory with Sand
  case object FuneralPlains extends Territory with Sand
  case object TheGreaterFlat extends Territory with Sand
  case object TheGreatFlat extends Territory with Sand
  case object HabbanyaErg extends Territory with Sand
  case object HabbanyaRidgeFlat extends Territory with Sand
  case object TueksSietch extends Territory with Stronghold
  case object Arrakeen extends Territory with Stronghold with HasOrnithopters
  case object Carthag extends Territory with Stronghold with HasOrnithopters
  case object SietchTabr extends Territory with Stronghold
  case object HabbanyaRidgeSietch extends Territory with Stronghold
  case object FalseWallSouth extends Territory with Rock
  case object PastyMesa extends Territory with Rock
  case object ShieldWall extends Territory with Rock
  case object RimWallWest extends Territory with Rock
  case object PlasticBasin extends Territory with Rock
  case object FalseWallWest extends Territory with Rock
  case object FalseWallEast extends Territory with Rock
  case object PolarSink extends Territory with Ice
}