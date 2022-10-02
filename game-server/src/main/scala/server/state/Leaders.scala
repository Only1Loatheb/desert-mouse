package server.state

import game.state.faction._
import game.state.leaders._

object leaders {

  val leadersByFaction: Faction => Set[Leader] = {
    case Atreides => Set(
      DuncanIdaho,
      DrWellingtonYueh,
      GurneyHalleck,
      LadyJessica,
      ThufirHawat,
    )
    case BeneGesserit => Set(
      Alia,
      MargotLadyFenring,
      PrincessIrulan,
      ReverendMotherRamallo,
      WannaMarcus,
    )
    case Emperor => Set(
      Bashar,
      Burseg,
      Caid,
      CaptainAramsham,
      CountHasimirFenring,
    )
    case Fremen => Set(
      Chani,
      Jamis,
      Otheym,
      ShadoutMapes,
      Stilgar,
    )
    case Guild => Set(
      EsmarTuek,
      MasterBewt,
      Representative,
      SooSooSook,
      StabanTuek,
    )
    case Harkonnen => Set(
      CaptainIakinNefud,
      FeydRautha,
      BeastRabban,
      PiterDeVries,
      UmmanKudu,
    )
  }

}