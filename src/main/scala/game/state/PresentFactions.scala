package game.state

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection._

object present_factions {
  type PresentFactions = Set[faction.Faction] Refined MinSize[2]
}
