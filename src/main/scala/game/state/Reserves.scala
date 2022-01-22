package game.state

import game.state.present_factions.PresentFactions
import game.state.army._
import game.state.faction._

object reserves {

  object Reserves {
    val startingReserves: Faction => Army = {
      case Fremen       => FremenArmy(0, 0)
      case Atreides     => AtreidesArmy(10)
      case Harkonnen    => HarkonnenArmy(10)
      case BeneGesserit => BeneGesseritArmy(0, 19)
      case Guild        => GuildArmy(15)
      case Emperor      => EmperorArmy(15, 5)
    }

    def apply(presentFactions: PresentFactions): Reserves = {
      Reserves(
        presentFactions.value.map(faction => (faction, startingReserves(faction))).toMap
      )
    }
  }

  final case class Reserves(armies: Map[Faction, Army])
}
