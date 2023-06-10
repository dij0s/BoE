package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.utils.Utils

class DirectedProjectile(emitter: Entity, target: Entity) extends Projectile(emitter) {
  private val step: Position = findStep()

  override def getGroupName(): CollisionGroupNames = {
    CollisionGroupNames.EnemyProjectile
  }

  override def getNewCoordinates(currentPos: Position): Position = {
    return new Position(currentPos.x + step.x, currentPos.y + step.y)
  }

  def findStep() : Position = {
    val targetCenter: Position = Utils.getEntityCenter(target)
    val emitterCenter: Position = Utils.getEntityCenter(emitter)
    val posDiff = new Position(targetCenter.x - emitterCenter.x, targetCenter.y - emitterCenter.y)
    val norme = Utils.getVectorLength(posDiff)
    val stepX = posDiff.x.toDouble / norme * this.speed
    val stepY = posDiff.y.toDouble / norme * this.speed
    return new Position(Math.round(stepX).toInt, Math.round(stepY).toInt)
  }



}
