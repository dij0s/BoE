package ch.hevs.boe.utils

import ch.hevs.boe.physics.PhysicalObject
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.g2d.TextureRegion

object Utils {

  def drawPhysicalObject(obj: PhysicalObject, g: GdxGraphics): Unit = {
    val screenH: Float = g.getScreenHeight.asInstanceOf[Float]
    val centerX: Float = obj.position.x.asInstanceOf[Float] + (obj.width / 2f)
    val centerY: Float = screenH - obj.position.y - (obj.height / 2f)
    g.drawRectangle(centerX, centerY, obj.width, obj.height, 0)
  }
  
//  def drawSprite(sprites: TextureRegion, obj: PhysicalObject, g: GdxGraphics): Unit = {
//    val screenH: Float = g.getScreenHeight.asInstanceOf[Float]
//    var rotationYpad: Float = 0f
//    var rotationXpad: Float = 0f
//
//    rotationAngle match {
//      case 180f => rotationYpad = obj.height; rotationXpad = obj.width
//      case _ => 0f
//    }
//
//    val updatedY: Float = screenH - obj.position.y - obj.height + rotationYpad
//    val updatedX: Float = obj.position.x + rotationXpad
//
//    // must make use of rotation angle
//    g.draw(sprites, updatedX, updatedY, 0f, 0f, obj.width, obj.height, 1f, 1f, rotationAngle)
//  }
}
