package ch.hevs.boe

import ch.hevs.boe.stage.rooms.TestingRoom
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.components.physics.utils.PhysicsScreenBoundaries
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.desktop.physics.DebugRenderer
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World

class Game extends PortableApplication(900, 600) {
  val startRoom: TestingRoom = new TestingRoom

  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")
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

