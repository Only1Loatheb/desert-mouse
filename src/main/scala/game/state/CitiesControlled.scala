package game.state

import game.state.faction._
import game.state.dune_map._
import game.state.present_factions.PresentFactions

object cities_controlled {

  final case class CitiesControlled(armies: Map[Faction, Set[Territory]])

  /** It may be dynamic, but in base game it is not
    */
  private val startingCitiesControlled: Map[Faction, Set[Territory]] = Map(
    Fremen -> Set(),
    Atreides -> Set(Arrakeen),
    Harkonnen -> Set(Carthag),
    BeneGesserit -> Set(),
    Guild -> Set(TueksSietch),
    Emperor -> Set()
  )

  object CitiesControlled {

    def apply(presentFactions: PresentFactions): CitiesControlled = {
      CitiesControlled(startingCitiesControlled.filter { 
        case (k, _) => presentFactions.value.contains(k)
      })
    }
  }
}
