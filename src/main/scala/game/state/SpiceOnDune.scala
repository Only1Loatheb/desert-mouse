package game.state

import game.state.dune_map._
import game.state.sector._
import game.state.spice.SpiceOnDune._

object spice {

  final case class SpiceOnDune(spice: Map[Territory, Int]) {

    def addSpice(territory: Territory): SpiceOnDune = {
      val spiceToAdd = initialSpiceAmount(territory)
      SpiceOnDune(spice.updatedWith(territory)(updateSpice(spiceToAdd)))
    }
  }

  object SpiceOnDune {
    val noSpiceOnDune: SpiceOnDune = SpiceOnDune(Map())
    
    private def updateSpice(spiceToAdd: Int)(existingSpice: Option[Int]): Option[Int] = {
      Some(existingSpice.getOrElse(0) + spiceToAdd)
    }

    val spiceSector: PartialFunction[Territory, Sector] = {
      case CielagoSouth => Sector1
      case CielagoNorth => Sector2
      case SouthMesa => Sector4
      case RedChasm => Sector6
      case TheMinorErg => Sector7
      case SihayaRidge => Sector8
      case OldGap => Sector9
      case BrokenLand => Sector11
      case HaggaBasin => Sector12
      case RockOutcroppings => Sector13
      case FuneralPlains => Sector14
      case TheGreatFlat => Sector14
      case HabbanyaErg => Sector15
      case WindPassNorth => Sector16
      case HabbanyaRidgeFlat => Sector17
    }

    val initialSpiceAmount: PartialFunction[Territory, Int] = {
      case CielagoSouth => 12
      case CielagoNorth => 8
      case SouthMesa => 10
      case RedChasm => 8
      case TheMinorErg => 8
      case SihayaRidge => 6
      case OldGap => 6
      case BrokenLand => 8
      case HaggaBasin => 6
      case RockOutcroppings => 6
      case FuneralPlains => 6
      case TheGreatFlat => 10
      case HabbanyaErg => 8
      case WindPassNorth => 6
      case HabbanyaRidgeFlat => 10
    }
  }
}