package ch.hevs.boe

import ch.hevs.boe.stage.rooms.TestingRoom
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Game extends PortableApplication(900, 600) {
  private var wallSprite: Spritesheet = null
  private var startRoom: TestingRoom = null

  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")

    wallSprite = new Spritesheet("data/sprites/cave_room_wall.png", 278, 30)
    startRoom = new TestingRoom(wallSprite)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    g.drawFPS()
    startRoom.draw(g)
  }
}

object BoERunner extends App {
  new Game
}

