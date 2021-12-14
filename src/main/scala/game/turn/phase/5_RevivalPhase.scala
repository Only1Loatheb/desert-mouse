// package game.turn.phase

// import scala.annotation.tailrec
// import scala.annotation.nowarn
// import eu.timepit.refined.types.numeric.PosInt

// import game.turn.phase.phase.Phase
// import game.state.faction.Faction
// import game.state.faction
// import game.state.treachery_deck.TreacheryCard
// import game.state.faction_spice.FactionSpice
// import game.state.treachery_cards.TreacheryCards
// import game.bot_interface.base.Bots
// import game.turn.phase.phase.Phase
// import game.state.faction_spice
// import game.state.faction.Faction
// import game.bot_interface.base.BotInterface
// import game.state.table_state.TableState
// import game.bot_interface.base
// import game.state.army.Army

// object revival_phase {

//   val revivalPhase: Phase = gameState => {

//     val revivalDecisions = gameState.bots
//       .map(getReviveArmyDecisions(gameState.tableState))

//     val newFactionToSpice = revivalDecisions
//       .map { case (faction, update) => faction -> update._1 }

//     val newFactionToBots = revivalDecisions
//       .map { case (faction, update) => faction -> update._2 }

//     val newTableState = gameState.tableState
//       .copy(factionSpice = faction_spice.FactionSpice(newFactionToSpice))
//     gameState.copy(tableState = newTableState, bots = newFactionToBots)
//   }

//   type RevivalDecision = base.BotDecision[Option[Army]]

//   @inline
//   private final def getReviveArmyDecisions(tableState: TableState): ((Faction, BotInterface)) => (Faction, RevivalDecision) = {
//     case (faction, bot) => (faction, bot.reviveArmy(tableState.view(faction)))
//   }

// }
