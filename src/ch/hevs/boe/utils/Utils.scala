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

  def getEntityCenterWithChild(e: PhysicalObject, width: Int, _height: Int = -1): Position = {
    var height = _height
    if(_height == -1) height = width
    val center = getEntityCenter(e)
    center.x -= width / 2
    center.y -= height / 2
    return center
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

  def getAngleBetweenVectors(p1: Position, p2: Position): Int = {
    // -> arccos( (p1.x))
    val scalar: Double = p1.x * p2.x + p1.y * p2.y
    val p1Norm:Double = Math.sqrt(Math.pow(p1.x, 2) + Math.pow(p1.y, 2))
    val p2Norm: Double = Math.sqrt(Math.pow(p2.x, 2) + Math.pow(p2.y, 2))
    val res: Double = Math.acos(scalar / (p1Norm * p2Norm))
    return Math.round(res * 180 / Math.PI).toInt
  }

  def equalWithMargin(x: Int, y: Int, margin: Int): Boolean = {
    val minX = x - margin
    val maxX = x + margin
    if(y <= maxX && y >= minX) {
      return true
    }
    return false
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
