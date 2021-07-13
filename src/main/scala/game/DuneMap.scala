package game.dune_map

import scalax.collection.Graph // or scalax.collection.mutable.Graph
import scalax.collection.GraphPredef._
import scalax.collection.GraphEdge._

import game.sector._
import scalax.collection.edge.LUnDiEdge
import scalax.collection.edge.Implicits._
import scalax.collection.edge.LBase.LEdgeImplicits

sealed trait DuneMapNode
sealed trait Sand
sealed trait Rock
sealed trait Ice
sealed trait City

object DuneMap {
  type Territory = Product with DuneMapNode with Serializable
  
  type DuneMapEdge = UnDiEdge[Territory]
  type GetSectorOnEdgeEnd = (Sector, Territory) => Set[Sector]
  type DuneMapGraph = Graph[Territory, LUnDiEdge]

  object LabelToGetSectorOnEdgeEndConversionImplicit extends LEdgeImplicits[GetSectorOnEdgeEnd]

  val duneMap: DuneMapGraph = {
    val nodes: Set[Territory] = Set(
        Meridan
      , CielagoSouth
      , CielagoEast
      , SouthMesa
      , RedChasm
      , GaraKulon
      , SihayaRidge
      , Basin
      , OldGap
      , Arsunt
      , HaggaBasin
      , WindPassNorth
      , HargPass
      , WindPass
      , CielagoWest
      , CielagoDepression
      , CielagoNorth
      , TheMinorErg
      , HoleInTheRock
      , Tsimpo
      , ImperialBasin
      , BrokenLand
      , RockOutcroppings
      , BlightOfTheCliff
      , FuneralPlains
      , TheGreaterFlat
      , TheGreatFlat
      , HabbanyaErg
      , HabbanyaRidgeFlat
      , TueksSietch
      , Arrakeen
      , Carthag
      , SietchTabr
      , HabbanyaRidgeSietch
      , FalseWallSouth
      , PastyMesa
      , ShieldWall
      , RimWallWest
      , PlasticBasin
      , FalseWallWest
      , FalseWallEast
      , PolarSink
      )
    val edges: Set[LUnDiEdge[Territory] with EdgeCopy[LUnDiEdge]{type L1 = GetSectorOnEdgeEnd}] = Set(
        (OldGap ~+ BrokenLand)(((_ , _) => Set(Sector10)): GetSectorOnEdgeEnd)
      , (OldGap ~+ Tsimpo)(((_, _) => Set(Sector10)): GetSectorOnEdgeEnd)
      , (OldGap ~+ ImperialBasin)(((_, _) => Set(Sector9)): GetSectorOnEdgeEnd)
      , (OldGap ~+ Arrakeen)(((_, _) => Set(Sector9)): GetSectorOnEdgeEnd)
      , (OldGap ~+ RimWallWest)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (OldGap ~+ Basin)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (Basin ~+ RimWallWest)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (Basin ~+ HoleInTheRock)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (Basin ~+ SihayaRidge)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (SihayaRidge ~+ HoleInTheRock)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (SihayaRidge ~+ ShieldWall)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (SihayaRidge ~+ GaraKulon)(((_, t) => t match {case SihayaRidge => Set(Sector8); case GaraKulon => Set(Sector7)}): GetSectorOnEdgeEnd)
      , (GaraKulon ~+ ShieldWall)(((_, _) => Set(Sector7)): GetSectorOnEdgeEnd)
      , (GaraKulon ~+ PastyMesa)(((_, _) => Set(Sector7)): GetSectorOnEdgeEnd)
      , (PastyMesa ~+ ShieldWall)(((_, _) => Set(Sector7)): GetSectorOnEdgeEnd)
      , (PastyMesa ~+ TheMinorErg)(((prevSector, _) => Set(prevSector)): GetSectorOnEdgeEnd)
      , (PastyMesa ~+ FalseWallSouth)(((_, _) => Set(Sector4)): GetSectorOnEdgeEnd)
      , (PastyMesa ~+ TueksSietch)(((_, _) => Set(Sector4)): GetSectorOnEdgeEnd)
      , (PastyMesa ~+ SouthMesa)(((_, _) => Set(Sector5)): GetSectorOnEdgeEnd)
      , (PastyMesa ~+ RedChasm)(((_, _) => Set(Sector6)): GetSectorOnEdgeEnd)
      , (RedChasm ~+ SouthMesa)(((_, t) => t match {case RedChasm => Set(Sector6); case SouthMesa => Set(Sector5)}): GetSectorOnEdgeEnd)
      , (SouthMesa ~+ TueksSietch)(((_, _) => Set(Sector4)): GetSectorOnEdgeEnd)
      , (SouthMesa ~+ FalseWallSouth)(((_, _) => Set(Sector3)): GetSectorOnEdgeEnd)
      , (SouthMesa ~+ CielagoEast)(((_, _) => Set(Sector3)): GetSectorOnEdgeEnd)
      , (CielagoEast ~+ FalseWallSouth)(((_, _) => Set(Sector3)): GetSectorOnEdgeEnd)
      , (CielagoEast ~+ CielagoNorth)(((_, _) => Set(Sector2)): GetSectorOnEdgeEnd)
      , (CielagoEast ~+ CielagoDepression)(((_, _) => Set(Sector2)): GetSectorOnEdgeEnd)
      , (CielagoEast ~+ CielagoSouth)(((_, _) => Set(Sector2)): GetSectorOnEdgeEnd)
      , (CielagoSouth ~+ CielagoDepression)(((prevSector, _) => Set(prevSector)): GetSectorOnEdgeEnd)
      , (CielagoSouth ~+ Meridan)(((_, _) => Set(Sector1)): GetSectorOnEdgeEnd)
      , (Meridan ~+ CielagoDepression)(((prevSector, _) => Set(prevSector)): GetSectorOnEdgeEnd)
      , (Meridan ~+ CielagoWest)(((_, _) => Set(Sector0)): GetSectorOnEdgeEnd)
      , (Meridan ~+ HabbanyaRidgeFlat)(((_, t) => t match {case Meridan => Set(Sector0); case HabbanyaRidgeFlat => Set(Sector17)}): GetSectorOnEdgeEnd)
      , (HabbanyaRidgeFlat ~+ CielagoWest)(((_, _) => Set(Sector17)): GetSectorOnEdgeEnd)
      , (HabbanyaRidgeFlat ~+ FalseWallWest)(((prevSector, _) => Set(prevSector)): GetSectorOnEdgeEnd)
      , (HabbanyaRidgeFlat ~+ HabbanyaErg)(((_, _) => Set(Sector16)): GetSectorOnEdgeEnd)
      , (HabbanyaRidgeFlat ~+ HabbanyaRidgeSietch)(((_, _) => Set(Sector16)): GetSectorOnEdgeEnd)
      , (HabbanyaErg ~+ FalseWallWest)(((_, _) => Set(Sector16)): GetSectorOnEdgeEnd)
      , (HabbanyaErg ~+ TheGreaterFlat)(((_, _) => Set(Sector15)): GetSectorOnEdgeEnd)
      , (TheGreaterFlat ~+ FalseWallWest)(((_, _) => Set(Sector15)): GetSectorOnEdgeEnd)
      , (TheGreaterFlat ~+ WindPass)(((_, _) => Set(Sector15)): GetSectorOnEdgeEnd)
      , (TheGreaterFlat ~+ TheGreatFlat)(((_, t) => t match {case TheGreaterFlat => Set(Sector15); case TheGreatFlat => Set(Sector14)}): GetSectorOnEdgeEnd)
      , (TheGreatFlat ~+ WindPass)(((_, _) => Set(Sector14)): GetSectorOnEdgeEnd)
      , (TheGreatFlat ~+ PlasticBasin)(((_, t) => t match {case TheGreatFlat => Set(Sector14); case PlasticBasin => Set(Sector13)}): GetSectorOnEdgeEnd)
      , (TheGreatFlat ~+ FuneralPlains)(((_, _) => Set(Sector14)): GetSectorOnEdgeEnd)
      , (FuneralPlains ~+ PlasticBasin)(((_, t) => t match {case FuneralPlains => Set(Sector14); case PlasticBasin => Set(Sector13)}): GetSectorOnEdgeEnd)
      , (FuneralPlains ~+ BlightOfTheCliff)(((_, _) => Set(Sector14)): GetSectorOnEdgeEnd)
      , (BlightOfTheCliff ~+ PlasticBasin)(((_, _) => Set(Sector13)): GetSectorOnEdgeEnd)
      , (BlightOfTheCliff ~+ SietchTabr)(((_, _) => Set(Sector13)): GetSectorOnEdgeEnd)
      , (BlightOfTheCliff ~+ RockOutcroppings)(((_, _) => Set(Sector13)): GetSectorOnEdgeEnd)
      , (RockOutcroppings ~+ SietchTabr)(((_, _) => Set(Sector13)): GetSectorOnEdgeEnd)
      , (RockOutcroppings ~+ PlasticBasin)(((_, _) => Set(Sector12)): GetSectorOnEdgeEnd) // ???
      , (RockOutcroppings ~+ BrokenLand)(((_, t) => t match {case RockOutcroppings => Set(Sector12); case BrokenLand => Set(Sector11)}): GetSectorOnEdgeEnd)
      , (BrokenLand ~+ PlasticBasin)(((_, _) => Set(Sector11)): GetSectorOnEdgeEnd)
      , (BrokenLand ~+ Tsimpo)(((prevSector, _) => Set(prevSector)): GetSectorOnEdgeEnd)
      , (Arrakeen ~+ ImperialBasin)(((_, _) => Set(Sector9)): GetSectorOnEdgeEnd)
      , (Arrakeen ~+ RimWallWest)(((_, t) => t match {case Arrakeen => Set(Sector9); case RimWallWest => Set(Sector8)}): GetSectorOnEdgeEnd)
      , (RimWallWest ~+ ImperialBasin)(((_, t) => t match {case RimWallWest => Set(Sector8); case ImperialBasin => Set(Sector9)}): GetSectorOnEdgeEnd)
      , (RimWallWest ~+ HoleInTheRock)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (HoleInTheRock ~+ ImperialBasin)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (HoleInTheRock ~+ ShieldWall)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (ShieldWall ~+ ImperialBasin)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (ShieldWall ~+ FalseWallEast)(((prevSector, t) => t match {case ShieldWall => Set(Sector7, Sector8); case FalseWallEast => Set(prevSector)}): GetSectorOnEdgeEnd)
      , (ShieldWall ~+ TheMinorErg)(((_, _) => Set(Sector7)): GetSectorOnEdgeEnd)
      , (TheMinorErg ~+ FalseWallEast)(((prevSector, t) => t match {case TheMinorErg => if (prevSector == Sector8) Set(Sector7) else Set(prevSector); case FalseWallEast => Set(prevSector)}): GetSectorOnEdgeEnd)
      , (TheMinorErg ~+ HargPass)(((_, _) => Set(Sector4)): GetSectorOnEdgeEnd)
      , (TheMinorErg ~+ FalseWallSouth)(((_, _) => Set(Sector4)): GetSectorOnEdgeEnd)
      , (FalseWallSouth ~+ HargPass)(((prevSector, _) => Set(prevSector)): GetSectorOnEdgeEnd)
      , (FalseWallSouth ~+ CielagoNorth)(((_, t) => t match {case FalseWallSouth => Set(Sector3); case CielagoNorth => Set(Sector2)}): GetSectorOnEdgeEnd)
      , (FalseWallSouth ~+ TueksSietch)(((_, _) => Set(Sector4)): GetSectorOnEdgeEnd)
      , (CielagoNorth ~+ HargPass)(((_, t) => t match {case CielagoNorth => Set(Sector2); case HargPass => Set(Sector3)}): GetSectorOnEdgeEnd)
      , (CielagoNorth ~+ PolarSink)(((_, t) => t match {case CielagoNorth => Set(Sector0, Sector1, Sector2); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (CielagoNorth ~+ WindPassNorth)(((_, t) => t match {case CielagoNorth => Set(Sector0); case WindPassNorth => Set(Sector17)}): GetSectorOnEdgeEnd)
      , (CielagoNorth ~+ CielagoWest)(((_, _) => Set(Sector0)): GetSectorOnEdgeEnd)
      , (CielagoNorth ~+ CielagoDepression)(((prevSector, _) => Set(prevSector)): GetSectorOnEdgeEnd)
      , (CielagoDepression ~+ CielagoWest)(((_, _) => Set(Sector0)): GetSectorOnEdgeEnd)
      , (CielagoWest ~+ WindPassNorth)(((_, t) => t match {case CielagoWest => Set(Sector0); case WindPassNorth => Set(Sector17)}): GetSectorOnEdgeEnd)
      , (CielagoWest ~+ WindPass)(((_, t) => t match {case CielagoWest => Set(Sector17); case WindPass => Set(Sector16)}): GetSectorOnEdgeEnd)
      , (CielagoWest ~+ FalseWallWest)(((_, _) => Set(Sector17)): GetSectorOnEdgeEnd)
      , (FalseWallWest ~+ WindPass)(((_, _) => Set(Sector15,Sector16)): GetSectorOnEdgeEnd)
      , (WindPass ~+ CielagoWest)(((_, t) => t match {case WindPass => Set(Sector16); case CielagoWest => Set(Sector17)}): GetSectorOnEdgeEnd)
      , (WindPass ~+ WindPassNorth)(((_, _) => Set(Sector16)): GetSectorOnEdgeEnd)
      , (WindPass ~+ PolarSink)(((_, t) => t match {case WindPass => Set(Sector13, Sector14, Sector15); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (WindPass ~+ HaggaBasin)(((_, t) => t match {case WindPass => Set(Sector13); case HaggaBasin => Set(Sector12)}): GetSectorOnEdgeEnd)
      , (WindPass ~+ PlasticBasin)(((_, _) => Set(Sector13)): GetSectorOnEdgeEnd)
      , (PlasticBasin ~+ HaggaBasin)(((_, _) => Set(Sector12)): GetSectorOnEdgeEnd)
      , (PlasticBasin ~+ Tsimpo)(((prevSector, t) => t match {case PlasticBasin => Set(Sector11, Sector12); case Tsimpo => Set(Sector11, Sector12)}): GetSectorOnEdgeEnd)
      , (PlasticBasin ~+ SietchTabr)(((_, _) => Set(Sector13)): GetSectorOnEdgeEnd)
      , (Tsimpo ~+ HaggaBasin)(((prevSector, t) => t match {case Tsimpo => Set(prevSector); case HaggaBasin => Set(Sector11, Sector12)}): GetSectorOnEdgeEnd)
      , (Tsimpo ~+ Carthag)(((_, _) => Set(Sector10)): GetSectorOnEdgeEnd)
      , (Tsimpo ~+ ImperialBasin)(((_, _) => Set(Sector10)): GetSectorOnEdgeEnd)
      , (ImperialBasin ~+ FalseWallEast)(((_, _) => Set(Sector8)): GetSectorOnEdgeEnd)
      , (ImperialBasin ~+ PolarSink)(((_, t) => t match {case ImperialBasin => Set(Sector9); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (ImperialBasin ~+ Arsunt)(((_, _) => Set(Sector10)): GetSectorOnEdgeEnd)
      , (ImperialBasin ~+ Carthag)(((_, _) => Set(Sector10)): GetSectorOnEdgeEnd)
      , (FalseWallEast ~+ PolarSink)(((_, t) => t match {case FalseWallEast => Set(Sector4, Sector5, Sector6, Sector7, Sector8); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (FalseWallEast ~+ HargPass)(((_, _) => Set(Sector4)): GetSectorOnEdgeEnd)
      , (HargPass ~+ PolarSink)(((_, t) => t match {case HargPass => Set(Sector3); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (WindPassNorth ~+ PolarSink)(((_, t) => t match {case WindPassNorth => Set(Sector16, Sector17); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (HaggaBasin ~+ PolarSink)(((_, t) => t match {case HaggaBasin => Set(Sector12); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (HaggaBasin ~+ Arsunt)(((_, _) => Set(Sector11)): GetSectorOnEdgeEnd)
      , (Arsunt ~+ PolarSink)(((_, t) => t match {case Arsunt => Set(Sector10, Sector11); case PolarSink => Set(FakePolarSector)}): GetSectorOnEdgeEnd)
      , (Arsunt ~+ Carthag)(((_, _) => Set(Sector10)): GetSectorOnEdgeEnd)
    )
    Graph.from(nodes, edges)
  }
}

  case object Meridan extends Product with DuneMapNode with Serializable with Sand
  case object CielagoSouth extends Product with DuneMapNode with Serializable with Sand
  case object CielagoEast extends Product with DuneMapNode with Serializable with Sand
  case object SouthMesa extends Product with DuneMapNode with Serializable with Sand
  case object RedChasm extends Product with DuneMapNode with Serializable with Sand
  case object GaraKulon extends Product with DuneMapNode with Serializable with Sand
  case object SihayaRidge extends Product with DuneMapNode with Serializable with Sand
  case object Basin extends Product with DuneMapNode with Serializable with Sand
  case object OldGap extends Product with DuneMapNode with Serializable with Sand
  case object Arsunt extends Product with DuneMapNode with Serializable with Sand
  case object HaggaBasin extends Product with DuneMapNode with Serializable with Sand
  case object WindPassNorth extends Product with DuneMapNode with Serializable with Sand
  case object HargPass extends Product with DuneMapNode with Serializable with Sand
  case object WindPass extends Product with DuneMapNode with Serializable with Sand
  case object CielagoWest extends Product with DuneMapNode with Serializable with Sand
  case object CielagoDepression extends Product with DuneMapNode with Serializable with Sand
  case object CielagoNorth extends Product with DuneMapNode with Serializable with Sand
  case object TheMinorErg extends Product with DuneMapNode with Serializable with Sand
  case object HoleInTheRock extends Product with DuneMapNode with Serializable with Sand
  case object Tsimpo extends Product with DuneMapNode with Serializable with Sand
  case object ImperialBasin extends Product with DuneMapNode with Serializable with Sand
  case object BrokenLand extends Product with DuneMapNode with Serializable with Sand
  case object RockOutcroppings extends Product with DuneMapNode with Serializable with Sand
  case object BlightOfTheCliff extends Product with DuneMapNode with Serializable with Sand
  case object FuneralPlains extends Product with DuneMapNode with Serializable with Sand
  case object TheGreaterFlat extends Product with DuneMapNode with Serializable with Sand
  case object TheGreatFlat extends Product with DuneMapNode with Serializable with Sand
  case object HabbanyaErg extends Product with DuneMapNode with Serializable with Sand
  case object HabbanyaRidgeFlat extends Product with DuneMapNode with Serializable with Sand
  case object TueksSietch extends Product with DuneMapNode with Serializable with City
  case object Arrakeen extends Product with DuneMapNode with Serializable with City
  case object Carthag extends Product with DuneMapNode with Serializable with City
  case object SietchTabr extends Product with DuneMapNode with Serializable with City
  case object HabbanyaRidgeSietch extends Product with DuneMapNode with Serializable with City
  case object FalseWallSouth extends Product with DuneMapNode with Serializable with Rock
  case object PastyMesa extends Product with DuneMapNode with Serializable with Rock
  case object ShieldWall extends Product with DuneMapNode with Serializable with Rock
  case object RimWallWest extends Product with DuneMapNode with Serializable with Rock
  case object PlasticBasin extends Product with DuneMapNode with Serializable with Rock
  case object FalseWallWest extends Product with DuneMapNode with Serializable with Rock
  case object FalseWallEast extends Product with DuneMapNode with Serializable with Rock
  case object PolarSink extends Product with DuneMapNode with Serializable with Ice