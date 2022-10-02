package game.state

import game.state.faction._
import game.state.sector._
import game.state.dune_map._
import game.state.present_factions.PresentFactions

object strongholds_controlled {

  type StrongholdTerritory = Territory with Stronghold
    
  final case class StrongholdsControlled(factionToControlledStrongholds: Map[Faction, Set[StrongholdTerritory]])
}
