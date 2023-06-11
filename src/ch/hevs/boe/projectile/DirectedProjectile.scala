package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.utils.Utils
import ch.hevs.boe.utils.Utils.getStepTowardEntity

class DirectedProjectile(emitter: Entity, target: Entity) extends Projectile(emitter) {
  val res = getStepTowardEntity(emitter, target)

  private val step: Position = new Position((res._1 * speed).toInt, (res._2 * speed).toInt)

  override def getGroupName(): CollisionGroupNames = {
    CollisionGroupNames.EnemyProjectile
  }

  override def getNewCoordinates(currentPos: Position): Position = {
    return new Position(currentPos.x + step.x, currentPos.y + step.y)
  }



}