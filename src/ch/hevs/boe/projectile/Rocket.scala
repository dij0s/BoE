package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.utils.Utils.{getEntityCenter, getStepTowardEntity}
import ch.hevs.boe.utils.time.Timeout

object Rocket {
  private val START_VALUE: Double = 1.1
  private val STEP_INDEX : Double = 0.02
  private val WIDTH: Int = 25
  private val HEIGHT: Int = 25
}


class Rocket(emitter: Entity, target: Entity, emitterGroup: CollisionGroupNames = CollisionGroupNames.EnemyProjectile) extends Projectile(emitter, Rocket.WIDTH, Rocket.HEIGHT) {

  private var exploding: Boolean = false

  override def ttl_=(newVal: Int): Unit = return

  private var index: Double = Rocket.START_VALUE

  private val step = getStepTowardEntity(emitter, target)

  private def easeInQuint(x: Double): Double = {
    Math.pow(x, 4)
  }

  override def getNewCoordinates(currentPos: Position): Position = {
    val factor = easeInQuint(index)
    val newPos = new Position(currentPos.x + Math.round(factor * step._1).toInt, currentPos.y + Math.round(factor * step._2).toInt)
    index += Rocket.STEP_INDEX
    return newPos
  }

  override protected def _dispose(): Unit = {
    if (exploding) return
    exploding = true
    if(this.doDeathEffects) {
      new Explosion(this, damage)
    }
    super._dispose()
  }

  override def getCollisionGroup(): CollisionGroupNames = emitterGroup
}
