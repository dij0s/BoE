package ch.hevs.boe.entity.mob

import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.{DefaultEntityStatistics, EntityStatistics}
import ch.hevs.boe.physics.Position

object Fly extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = 0
  override val DEFAULT_HP: Int = 3
  override val DAMAGE_DEFAULT: Int = 1
  override val SPEED_DEFAULT: Int = 1
  override val SIZE_DEFAULT: Int = 25
}

class Fly(pos: Position, cb: (Mob) => Unit) extends Mob(pos, Fly.SIZE_DEFAULT, Fly.SIZE_DEFAULT, cb) {
  override protected val contactDamage: Int = Fly.DAMAGE_DEFAULT
  override protected var _hp: Int = Fly.DEFAULT_HP
  override var fireRate: Double = Fly.FIRE_RATE_DEFAULT
  override var damage: Int = Fly.DAMAGE_DEFAULT
  override var speed: Int = Fly.SPEED_DEFAULT
  override var size: Int = Fly.SIZE_DEFAULT
}
