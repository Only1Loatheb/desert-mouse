package game.state

object treachery_deck {

  sealed trait TreacheryCard extends Serializable with Product
  sealed trait WeaponCard
  sealed trait DefenseCard

  sealed trait TreacheryType
  case object Projectile extends TreacheryType
  case object Poison extends TreacheryType

  case object Worthless extends TreacheryCard
  case class Weapon(weapon: TreacheryType) extends TreacheryCard with WeaponCard
  case class Defense(defense: TreacheryType) extends TreacheryCard with DefenseCard
  case object Lasgun extends TreacheryCard with WeaponCard // everything dies if uponent uses Projectile defense

  case object FamilyAtomics extends TreacheryCard
  case object Harj extends TreacheryCard // Play during movement Phase. Make an extra on-plant force movement subject to normal movement rules. The forces you move may be a group you have already moved this phase or another group
  case object TleilaxuGhola extends TreacheryCard
  case object WeatherControl extends TreacheryCard

  case object Karma extends TreacheryCard
  case object TruthTrance extends TreacheryCard
  case object CheapHero extends TreacheryCard

}