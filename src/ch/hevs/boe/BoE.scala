package ch.hevs.boe

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Game extends PortableApplication(900, 600) {

  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")
    GameplayManager.init()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    GameplayManager.gameTick(g)
  }
}

object BoERunner extends App {
  new Game
}

