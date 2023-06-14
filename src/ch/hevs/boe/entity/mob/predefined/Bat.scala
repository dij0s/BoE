package ch.hevs.boe.entity.mob.predefined

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.DirectedProjectile
import ch.hevs.boe.utils.time.Timeout

object Bat extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = 0
  override val DEFAULT_HP: Int = 7
  override val DAMAGE_DEFAULT: Int = 1
  override val SPEED_DEFAULT: Int = 1
  override val SIZE_DEFAULT: Int = 30
}

class Bat(pos: Position, cb: (Mob) => Unit) extends Mob(pos, Bat.SIZE_DEFAULT, Bat.SIZE_DEFAULT, cb) {
  override protected val contactDamage: Int = Bat.DAMAGE_DEFAULT
  override protected var _hp: Int = Bat.DEFAULT_HP
  override var fireRate: Double = Bat.FIRE_RATE_DEFAULT
  override var damage: Int = Bat.DAMAGE_DEFAULT
  override var speed: Int = Bat.SPEED_DEFAULT
  override var size: Int = Bat.SIZE_DEFAULT
  
  private var fireCooldown = false
  
  override def doGameplayTick(): Unit = fireToPlayer()
  
  private def fireToPlayer(): Unit = {
    if (fireCooldown) return
    fireCooldown = true
    new DirectedProjectile(this, GameplayManager.player)
    Timeout((1000 / fireRate).toInt) {
      fireCooldown = false
    }
  }
}
