package game.state

object dune_map {

  sealed trait Territory extends Product with Serializable
  sealed trait Sand extends Territory
  sealed trait Rock extends Territory
  sealed trait Ice extends Territory
  sealed trait Stronghold extends Territory
  sealed trait HasOrnithopters extends Stronghold

  case object Meridan extends Sand
  case object CielagoSouth extends Sand
  case object CielagoEast extends Sand
  case object SouthMesa extends Sand
  case object RedChasm extends Sand
  case object GaraKulon extends Sand
  case object SihayaRidge extends Sand
  case object Basin extends Sand
  case object OldGap extends Sand
  case object Arsunt extends Sand
  case object HaggaBasin extends Sand
  case object WindPassNorth extends Sand
  case object HargPass extends Sand
  case object WindPass extends Sand
  case object CielagoWest extends Sand
  case object CielagoDepression extends Sand
  case object CielagoNorth extends Sand
  case object TheMinorErg extends Sand
  case object HoleInTheRock extends Sand
  case object Tsimpo extends Sand
  case object ImperialBasin extends Sand
  case object BrokenLand extends Sand
  case object RockOutcroppings extends Sand
  case object BlightOfTheCliff extends Sand
  case object FuneralPlains extends Sand
  case object TheGreaterFlat extends Sand
  case object TheGreatFlat extends Sand
  case object HabbanyaErg extends Sand
  case object HabbanyaRidgeFlat extends Sand
  case object TueksSietch extends Stronghold
  case object Arrakeen extends HasOrnithopters
  case object Carthag extends HasOrnithopters
  case object SietchTabr extends Stronghold
  case object HabbanyaRidgeSietch extends Stronghold
  case object FalseWallSouth extends Rock
  case object PastyMesa extends Rock
  case object ShieldWall extends Rock
  case object RimWallWest extends Rock
  case object PlasticBasin extends Rock
  case object FalseWallWest extends Rock
  case object FalseWallEast extends Rock
  case object PolarSink extends Ice
}