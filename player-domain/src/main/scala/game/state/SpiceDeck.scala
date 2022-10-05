package game.state

import game.state.SpiceDeck.SpiceCard
import game.state.dune_map.SandWithSpiceBlows

final case class SpiceDeck(cards: List[SpiceCard])

object SpiceDeck {

  sealed trait SpiceCard extends Serializable with Product

  object SpiceCard {
    final case class SpiceBlow(territory: SandWithSpiceBlows) extends SpiceCard

    case object ShaiHulud extends SpiceCard

  }

}