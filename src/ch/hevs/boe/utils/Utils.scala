package ch.hevs.boe.utils

import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.g2d.TextureRegion

object Utils {

  def drawPhysicalObject(obj: PhysicalObject, g: GdxGraphics): Unit = {
    val screenH: Float = g.getScreenHeight.asInstanceOf[Float]
    val centerX: Float = obj.position.x.asInstanceOf[Float] + (obj.width / 2f)
    val centerY: Float = screenH - obj.position.y - (obj.height / 2f)
    g.drawRectangle(centerX, centerY, obj.width, obj.height, 0)
  }

  def getVectorLength(pos: Position): Double = {
    return Math.sqrt(Math.pow(pos.x, 2) + Math.pow(pos.y, 2))
  }


  def getEntityCenter(e: PhysicalObject): Position = {
    return new Position(e.position.x + e.width / 2, e.position.y + e.height / 2)
  }

  def getStepTowardEntity(emitter: Entity, target: Entity): (Double, Double) = {
    val targetCenter: Position = Utils.getEntityCenter(target)
    val emitterCenter: Position = Utils.getEntityCenter(emitter)
    val posDiff = new Position(targetCenter.x - emitterCenter.x, targetCenter.y - emitterCenter.y)
    val norme = Utils.getVectorLength(posDiff)
    val stepX = posDiff.x.toDouble / norme
    val stepY = posDiff.y.toDouble / norme
    return (stepX, stepY)
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
