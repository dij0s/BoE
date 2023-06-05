package ch.hevs.boe

import ch.hevs.boe.movable.Player
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.stage.rooms.TestingRoom
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Game extends PortableApplication(900, 600) {
  private var wallSprite: Spritesheet = null
  private var startRoom: TestingRoom = null
  private var player: Player = new Player(new Position(250, 250))

  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")

    wallSprite = new Spritesheet("data/sprites/cave_room_wall.png", 278, 30)
    startRoom = new TestingRoom(wallSprite)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    player.doGameplayTick()
    player.draw(g)
    g.drawFPS()
    CollisionManager.checkCollisions()
    startRoom.draw(g)
  }
}

object BoERunner extends App {
  new Game
}

