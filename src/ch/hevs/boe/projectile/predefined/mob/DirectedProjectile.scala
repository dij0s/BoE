package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.utils.Utils.{getEntityCenter, getStepTowardEntity}

class DirectedProjectile(emitter: Entity, target: Entity) extends BaseProjectile(emitter) {
  val res = getStepTowardEntity(getEntityCenter(emitter), getEntityCenter(target))

  private val step: Position = new Position((res._1 * speed).toInt, (res._2 * speed).toInt)

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.EnemyProjectile

  override def getNewCoordinates(currentPos: Position): Position = new Position(currentPos.x + step.x, currentPos.y + step.y)



}
