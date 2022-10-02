package game.state

object faction {
  sealed trait Faction extends Serializable with Product

  case object Atreides extends Faction
  case object Harkonnen extends Faction
  case object Fremen extends Faction
  case object Emperor extends Faction
  case object Guild extends Faction
  case object BeneGesserit extends Faction
}
