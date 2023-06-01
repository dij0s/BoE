package ch.hevs.boe

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

  var world: World = PhysicsWorld.getInstance()
  var square: PhysicsStaticBox = null
  var debugRenderer: DebugRenderer = null

  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")
    debugRenderer = new DebugRenderer()
    square = new PhysicsStaticBox(null, new Vector2(100, 100), 50, 50)
    new PhysicsScreenBoundaries(900, 600)
    

  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()

    debugRenderer.render(world, g.getCamera.combined)
    g.drawFPS()

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)
  }
}

object BoERunner extends App {
  new Game
}

