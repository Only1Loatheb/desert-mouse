package game.turn.phase

import game.turn.phase.phase.Phase
import game.state.faction.Faction
import game.state.faction
import game.state.treachery_deck.TreacheryCard
import game.state.faction_spice.FactionSpice
import game.state.treachery_cards.TreacheryCards

import scala.annotation.nowarn

object bidding_phase {

  /**
    * Before bidding starts, all players must declare how many Treachery Cards they hold.
    * The hand limit is 4. 
    * Players with 4 cards must pass during bidding
    */
  val biddingPhase: Phase = gameState => {
    val factionToCards = gameState.tableState.treacheryCards.factionToCards
    val biddingFactions = factionToCards
      .filter { case (faction, cards) => cards.size < factionCardsLimit(faction) }
      .keySet

    val (newTreacheryDeck, cards) = gameState.tableState.treacheryDeck
      .drawCards(biddingFactions.size)

    val factionOrder = gameState.tableState.turnState.factionInitiative
      .filter(biddingFactions.contains)

    val (newFactionToSpice, boughtFactionTrecheryCards) = bidding(
      factionOrder,
      cards,
      gameState.tableState.factionSpice.factionToSpice
    )

    val newFactionTrecheryCards = factionToCards ++ boughtFactionTrecheryCards // this is wrong
      
    val newTableState = gameState.tableState.copy(
      treacheryDeck = newTreacheryDeck,
      factionSpice = FactionSpice(newFactionToSpice),
      treacheryCards = TreacheryCards(newFactionTrecheryCards))
    gameState.copy(tableState = newTableState)
  }

  @nowarn
  private def bidding(
    factionOrder: List[Faction],
    cards: List[TreacheryCard],
    factionToSpice: Map[Faction, Int]
    ): (Map[Faction, Int], Map[Faction, Set[TreacheryCard]]) = {

    // val factionOrderCycle: LazyList[Faction] = 
    //   LazyList.from(factionOrder) #::: factionOrderCycle
    
    // (Map[Faction, Int].empty(), Map[Faction, Set[TreacheryCard]].empty())
    null
  }

  private def factionCardsLimit: Faction => Int = { 
    case faction.Harkonnen => 8
    case _ => 4
  }

}
