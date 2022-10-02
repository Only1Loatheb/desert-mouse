package server.state

import game.state.faction._
import game.state.sector._
import game.state.dune_map._
import game.state.present_factions.PresentFactions
import game.state.strongholds_controlled.StrongholdsControlled

object strongholds_controlled {

  val strongholds: Set[Stronghold] = dune_map.duneMap.getNodes
    .collect { case stronghold: Stronghold => stronghold }
  
  val strongholdsWithOrnithopters: Set[Stronghold] = strongholds
    .filter { case _: HasOrnithopters => true; case _ => false }
    
  implicit class StrongholdsControlledOps(value: StrongholdsControlled) {

    def factionsWithOrnithopters: Set[Faction] = {
      value.factionToControlledStrongholds
        .filter { case (_, controlled) => controlled.intersect(strongholdsWithOrnithopters).nonEmpty }
        .keySet
    }

    def factionsWithMostStrongholds: Seq[(Faction, Int)] = value.factionToControlledStrongholds
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
  private val startingStrongholdsControlled: Map[Faction, Set[Stronghold]] = Map(
    Fremen -> Set(),
    Atreides -> Set(Arrakeen),
    Harkonnen -> Set(Carthag),
    BeneGesserit -> Set(),
    Guild -> Set(TueksSietch),
    Emperor -> Set()
  )

  def init(presentFactions: PresentFactions): StrongholdsControlled = {
    StrongholdsControlled(startingStrongholdsControlled.filter {
      case (k, _) => presentFactions.value.contains(k)
    })
  }

}
