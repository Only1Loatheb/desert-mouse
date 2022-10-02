package server.turn.phase

import scala.annotation.tailrec
import scala.annotation.nowarn
import game.state.faction.Faction
import game.state.faction.Harkonnen
import game.state.treachery_deck.{DrawResult, TreacheryCard}
import game.state.faction_spice.FactionSpice
import game.state.treachery_cards.TreacheryCards
import game.state.spice.Spice
import game.turn.phase.phase.Phase
import game.player.player.Players


object bidding_phase {

  /** Before bidding starts, all players must declare how many Treachery Cards they hold. The hand
    * limit is 4. Players with 4 cards must pass during bidding
    */
  val _4_biddingPhase: Phase = gameState => {
    val factionToCards = gameState.tableState.treacheryCards.factionToCards
    val biddingFactions = factionToCards.filter { case (faction, cards) =>
      cards.size < factionCardsLimit(faction)
    }.keySet

    val DrawResult(newTreacheryDeck, drawnCards) = gameState.tableState.treacheryDeck
      .drawCards(biddingFactions.size)

    val factionOrder = gameState.tableState.turnState.factionInitiative
      .filter(biddingFactions.contains)

    val (newFactionToSpice, newFactionTrecheryCards, newPlayers) = bidding(
      factionOrder,
      drawnCards,
      gameState.tableState.factionSpice.factionToSpice,
      factionToCards,
      gameState.players
    )

    val newTableState = gameState.tableState.copy(
      treacheryDeck = newTreacheryDeck,
      factionSpice = FactionSpice(newFactionToSpice),
      treacheryCards = TreacheryCards(newFactionTrecheryCards)
    )
    gameState.copy(tableState = newTableState, players = newPlayers)
  }

  private def bidding(
    factionOrder: List[Faction],
    drawnCards: List[TreacheryCard],
    factionToSpice: Map[Faction, Spice],
    factionToCards: Map[Faction, Set[TreacheryCard]],
    players: Players
  ): (Map[Faction, Spice], Map[Faction, Set[TreacheryCard]], Players) = {

    val factionOrderCycle: LazyList[Faction] = LazyList.continually(factionOrder).flatten

    drawnCards.foldLeft((factionToSpice, factionToCards, players)) {
      case ((factionToSpice, factionToCards, bots), drawnCard) =>
        biddingNextCard(factionOrderCycle)(factionToSpice, factionToCards, bots, drawnCard)
    }
  }

  @nowarn
  private def biddingNextCard(
      factionOrderCycle: LazyList[Faction]
  )(
     factionToSpice: Map[Faction, Spice],
     factionToCards: Map[Faction, Set[TreacheryCard]],
     players: Players,
     drawnCard: TreacheryCard
  ): (Map[Faction, Spice], Map[Faction, Set[TreacheryCard]], Players) = {

    biddingAskNext(
      factionOrderCycle,
      drawnCard,
      factionToSpice,
      players,
      0
    )
  }

  @tailrec
  private def biddingAskNext(
                              factionOrderCycle: LazyList[Faction],
                              drawnCard: TreacheryCard,
                              factionToSpice: Map[Faction, Spice],
                              bots: Players,
                              consecutivePassCount: Int
  ): (Map[Faction, Spice], Map[Faction, Set[TreacheryCard]], Players) = {

    biddingAskNext(
      factionOrderCycle,
      drawnCard,
      factionToSpice,
      bots,
      consecutivePassCount + 1
    )
  }

  // move somewere else
  private val factionCardsLimit: Faction => Int = {
    case Harkonnen => 8
    case _ => 4
  }


  sealed trait BidAction
  object BidAction {
    case object Pass extends BidAction
    final case class Bid(value: Int) extends BidAction
  }

}
