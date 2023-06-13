package ch.hevs.boe.entity.mob

import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.mob.Firemen.{DAMAGE_DEFAULT, SIZE_DEFAULT, SPEED_DEFAULT}
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.{GameplayManager, projectile}
import ch.hevs.boe.projectile.{DirectedProjectile, Rocket}
import ch.hevs.boe.utils.time.Timeout

object Firemen extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = 1
  override val DEFAULT_HP: Int = 5
  override val DAMAGE_DEFAULT: Int = 1
  override val SPEED_DEFAULT: Int = 0
  override val SIZE_DEFAULT: Int = 15
}

class Firemen(pos: Position,
              callbackOnKilled: (Mob) => Unit) extends Mob(pos, 15, (15*1.5).toInt, callbackOnKilled) {
  override protected var _hp: Int = Firemen.DEFAULT_HP
  override var fireRate: Double = Firemen.FIRE_RATE_DEFAULT
  override var damage: Int = DAMAGE_DEFAULT
  override var speed: Int = SPEED_DEFAULT
  override var size: Int = SIZE_DEFAULT
  override protected val contactDamage: Int = 1

  private var fireCooldown = false

  override def doGameplayTick(): Unit = fireToPlayer()

  private def fireToPlayer(): Unit = {
    if(fireCooldown) return
    fireCooldown = true
    new DirectedProjectile(this, GameplayManager.player)
    Timeout((1000 / fireRate).toInt) {fireCooldown = false}
  }

}
