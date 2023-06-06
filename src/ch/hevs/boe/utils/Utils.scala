package ch.hevs.boe.utils

import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.gdx2d.lib.GdxGraphics

object Utils {

  def drawPhysicalObject(obj: PhysicalObject, g: GdxGraphics): Unit = {
    val screenH: Float = g.getScreenHeight.asInstanceOf[Float]
    g.drawRectangle(centerX, centerY, obj.width, obj.height, 0)
    // check next function
  }
  
  def convertCoordinatesSystem(obj: PhysicalObject, screenWidth: Float): Position = {
    val centerX: Float = obj.position.x.asInstanceOf[Float] + (obj.width / 2f)
    val centerY: Float = screenWidth - obj.position.y - (obj.height / 2f)
//    new Position(centerX, centerY)
    ???
  }
}
