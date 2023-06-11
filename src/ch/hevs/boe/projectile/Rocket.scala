package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.utils.Utils.getStepTowardEntity

object Rocket {
  private val START_VALUE: Double = 1.1
  private val STEP_INDEX : Double = 0.02
}


class Rocket(emitter: Entity, target: Entity, emitterGroup: CollisionGroupNames = CollisionGroupNames.EnemyProjectile) extends DirectedProjectile(emitter, target) {



  ttl = 100000
  private var index: Double = Rocket.START_VALUE

  private val step = getStepTowardEntity(emitter, target)

  override def getGroupName(): CollisionGroupNames = emitterGroup


  private def easeInQuint(x: Double): Double = {
    Math.pow(x, 5)
  }

  override def getNewCoordinates(currentPos: Position): Position = {
    val factor = easeInQuint(index)
    val newPos = new Position(currentPos.x + Math.round(factor * step._1).toInt, currentPos.y + Math.round(factor * step._2).toInt)
    index += Rocket.STEP_INDEX
    return newPos
  }
}
