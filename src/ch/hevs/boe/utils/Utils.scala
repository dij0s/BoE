package ch.hevs.boe.utils

import ch.hevs.boe.physics.{PhysicObject, Position}
import ch.hevs.gdx2d.lib.GdxGraphics

object Utils {

  def drawPhysicalObject(obj: PhysicObject, g: GdxGraphics): Unit = {
    val screenH = g.getScreenHeight.asInstanceOf[Float]
    val centerX = obj.position.x.asInstanceOf[Float] + (obj.width / 2f)
    val centerY = screenH - obj.position.y - (obj.height / 2f)
    g.drawRectangle(centerX, centerY, obj.width, obj.height, 0)
  }
}
