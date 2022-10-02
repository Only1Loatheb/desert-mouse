package game.state

import game.state.non_neg_int.NonNegInt

object army {

  sealed trait Army extends Serializable with Product 

  final case class AtreidesArmy(
      troops: NonNegInt
  ) extends Army 

  final case class HarkonnenArmy(
      troops: NonNegInt
  ) extends Army

  final case class FremenArmy(
      troops: NonNegInt,
      fedaykins: NonNegInt
  ) extends Army 

  final case class EmperorArmy(
      troops: NonNegInt,
      sardaukars: NonNegInt
  ) extends Army
  final case class GuildArmy(
      troops: NonNegInt
  ) extends Army 

  final case class BeneGesseritArmy(
      fighters: NonNegInt,
      advisors: NonNegInt
  ) extends Army 
}
