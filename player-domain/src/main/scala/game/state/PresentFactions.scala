package game.state

object present_factions {
  final case class PresentFactions(value: Set[faction.Faction])
}
