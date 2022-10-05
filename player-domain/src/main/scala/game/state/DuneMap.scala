package game.state

object dune_map {

  sealed trait Territory extends Product with Serializable
  sealed trait Sand extends Territory
  sealed trait SandWithSpiceBlows extends Sand
  sealed trait Rock extends Territory
  sealed trait Ice extends Territory
  sealed trait Stronghold extends Territory
  sealed trait StrongholdWithOrnithopters extends Stronghold

  case object Arrakeen extends StrongholdWithOrnithopters
  case object Arsunt extends Sand
  case object Basin extends Sand
  case object BlightOfTheCliff extends Sand
  case object BrokenLand extends SandWithSpiceBlows
  case object Carthag extends StrongholdWithOrnithopters
  case object CielagoDepression extends Sand
  case object CielagoEast extends Sand
  case object CielagoNorth extends SandWithSpiceBlows
  case object CielagoSouth extends SandWithSpiceBlows
  case object CielagoWest extends Sand
  case object FalseWallEast extends Rock
  case object FalseWallSouth extends Rock
  case object FalseWallWest extends Rock
  case object FuneralPlains extends SandWithSpiceBlows
  case object GaraKulon extends Sand
  case object HabbanyaErg extends SandWithSpiceBlows
  case object HabbanyaRidgeFlat extends SandWithSpiceBlows
  case object HabbanyaRidgeSietch extends Stronghold
  case object HaggaBasin extends SandWithSpiceBlows
  case object HargPass extends Sand
  case object HoleInTheRock extends Sand
  case object ImperialBasin extends Sand
  case object Meridan extends Sand
  case object OldGap extends SandWithSpiceBlows
  case object PastyMesa extends Rock
  case object PlasticBasin extends Rock
  case object PolarSink extends Ice
  case object RedChasm extends SandWithSpiceBlows
  case object RimWallWest extends Rock
  case object RockOutcroppings extends SandWithSpiceBlows
  case object ShieldWall extends Rock
  case object SietchTabr extends Stronghold
  case object SihayaRidge extends SandWithSpiceBlows
  case object SouthMesa extends SandWithSpiceBlows
  case object TheGreaterFlat extends Sand
  case object TheGreatFlat extends SandWithSpiceBlows
  case object TheMinorErg extends SandWithSpiceBlows
  case object Tsimpo extends Sand
  case object TueksSietch extends Stronghold
  case object WindPass extends Sand
  case object WindPassNorth extends SandWithSpiceBlows
}