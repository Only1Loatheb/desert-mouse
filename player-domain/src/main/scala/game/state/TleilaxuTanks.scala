package game.state

import game.state.army.Army
import game.state.faction.Faction
import game.state.leaders.Leader

object tleilaxu_tanks {
  final case class TleilaxuTanks(armies: Map[Faction,Army], leaders: Map[Faction,Set[Leader]])

}
