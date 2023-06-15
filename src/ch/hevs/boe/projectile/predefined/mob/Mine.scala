package ch.hevs.boe.projectile.predefined.mob

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.Projectile
import ch.hevs.boe.utils.time.Timer

object Mine {
  val LIFETIME = 10000
  val DEFAULT_SIZE = 10
}

class Mine(emitter: Entity, damage: Int, lifetime: Int = Mine.LIFETIME) extends Projectile(emitter, Mine.DEFAULT_SIZE, Mine.DEFAULT_SIZE) {

  override def ttl_=(newVal: Int): Unit = return // We're removing ttl as it's a mine !!!

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.EnemyProjectile

  override def _init(): Unit = {
    super._init()
    Timer.in(Mine.LIFETIME, () => {
      this.dispose()
    })
  }



  override def getNewCoordinates(currentPos: Position): Position = currentPos

  override def _dispose() = {
    super._dispose()
    if(this.doDeathEffects) {
      new Explosion(this, damage)
    }
  }
}