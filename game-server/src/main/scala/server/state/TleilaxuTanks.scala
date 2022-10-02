package server.state

import game.state.tleilaxu_tanks.TleilaxuTanks

object tleilaxu_tanks {
  def init: TleilaxuTanks = TleilaxuTanks(Map(), Map())
}
