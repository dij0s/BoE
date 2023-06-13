package ch.hevs.boe.entity.mob

import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.{DefaultEntityStatistics, EntityStatistics}
import ch.hevs.boe.physics.Position

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
}
