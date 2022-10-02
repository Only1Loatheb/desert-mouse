package server.state

import game.state.faction._
import game.state.sector._
import game.state.dune_map._
import game.state.present_factions.PresentFactions

object strongholds_controlled {

  type StrongholdTerritory = Territory with Stronghold

  val strongholds: Set[StrongholdTerritory] = dune_map.duneMap.getNodes
    .collect { case stronghold: StrongholdTerritory => stronghold }
  
  val strongholdsWithOrnithopters: Set[StrongholdTerritory] = strongholds
    .filter { case _: HasOrnithopters => true; case _ => false }
    
  final case class StrongholdsControlled(factionToControlledStrongholds: Map[Faction, Set[StrongholdTerritory]]) {

    def factionsWithOrnithopters: Set[Faction] = {
      factionToControlledStrongholds
        .filter { case (_, controlled) => controlled.intersect(strongholdsWithOrnithopters).nonEmpty }
        .keySet
    }

    def factionsWithMostStrongholds: Seq[(Faction, Int)] = factionToControlledStrongholds
      .toSeq
      .map { case (faction, controlled) => (faction, controlled.size) }
      .sortBy { case (_, controlled) => controlled }(Ordering[Int].reverse)
  }

  val strongholdSector: Stronghold => Sector = { 
    case TueksSietch => Sector4
    case Arrakeen => Sector9
    case Carthag => Sector10
    case SietchTabr => Sector13
    case HabbanyaRidgeSietch => Sector16 
  }

  /** It may be dynamic, but in base game it is not
    */
  private val startingStrongholdsControlled: Map[Faction, Set[StrongholdTerritory]] = Map(
    Fremen -> Set(),
    Atreides -> Set(Arrakeen),
    Harkonnen -> Set(Carthag),
    BeneGesserit -> Set(),
    Guild -> Set(TueksSietch),
    Emperor -> Set()
  )

  object StrongholdsControlled {

    def init(presentFactions: PresentFactions): StrongholdsControlled = {
      StrongholdsControlled(startingStrongholdsControlled.filter { 
        case (k, _) => presentFactions.value.contains(k)
      })
    }

  }
}
