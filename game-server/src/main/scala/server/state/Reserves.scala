package server.state

import game.state.present_factions.PresentFactions
import game.state.army._
import game.state.faction._
import game.state.non_neg_int.NonNegInt
import game.state.reserves.Reserves

object reserves {

  private implicit val nonNegIntImplicitConversion: Int => NonNegInt = NonNegInt(_).get

  private val startingReserves: Faction => Army = {
    case Fremen       => FremenArmy(0, 0)
    case Atreides     => AtreidesArmy(10)
    case Harkonnen    => HarkonnenArmy(10)
    case BeneGesserit => BeneGesseritArmy(0, 19)
    case Guild        => GuildArmy(15)
    case Emperor      => EmperorArmy(15, 5)
  }

  def init(presentFactions: PresentFactions): Reserves = {
    Reserves(
      presentFactions.value.map(faction => (faction, startingReserves(faction))).toMap
    )
  }
}
