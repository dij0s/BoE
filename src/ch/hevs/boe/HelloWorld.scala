package ch.hevs.boe

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.components.physics.primitives.PhysicsStaticBox
import ch.hevs.gdx2d.lib.physics.{AbstractPhysicsObject, PhysicsWorld}

import scala.collection.mutable.ListBuffer

class HelloWorldApplication extends PortableApplication(1200,1000) {
//  private var isaacSprites: Spritesheet = _
  
  private var physicsObjectsList: ListBuffer[AbstractPhysicsObject] = ListBuffer.empty
  
  override def onInit(): Unit = {
    setTitle("Hello world application")
//    isaacSprites = new Spritesheet("data/sprites/isaac_og_sheet.png", 64, 64)
//    physicsObjectsList.addOne(new PhysicsStaticBox(null, Vector[Int](100, 10), 40, 40))
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    
    PhysicsWorld.updatePhysics()
    
    g.drawFPS()
//    g.draw(isaacSprites.sprites(0)(0), 0, 0)
  }
}

object HelloWorldRunner extends App {
  new HelloWorldApplication
}
