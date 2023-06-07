package ch.hevs.boe

import ch.hevs.boe.draw.sprites.SpritesManager
import ch.hevs.boe.movable.Player
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.stage.room.SpawnRoom
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Game extends PortableApplication(900, 600) {
  private var startRoom: SpawnRoom = null
  private val player: Player = new Player(new Position(250, 250))

  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")
    startRoom = new SpawnRoom
    SpritesManager.init()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    player.doGameplayTick()
    startRoom.draw(g)
    player.draw(g)
    g.drawFPS()
    CollisionManager.checkCollisions()
  }
}

object BoERunner extends App {
  new Game
}

