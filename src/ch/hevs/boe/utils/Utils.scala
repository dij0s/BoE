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
  
  def drawSprite(sprites: TextureRegion, obj: PhysicalObject, g: GdxGraphics, rotationAngle: Float): Unit = {
    val screenH: Float = g.getScreenHeight.asInstanceOf[Float]
    val updatedY: Float = screenH - obj.position.y - obj.height
    // must make use of rotation angle
    g.draw(sprites, obj.position.x, updatedY, 0f, 0f, obj.width, obj.height, 1f, 1f, 0)
  }
}
