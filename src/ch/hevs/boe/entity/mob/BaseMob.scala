package ch.hevs.boe.entity.mob

import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position

class BaseMob(pos: Position) extends Mob(pos, Mob.SIZE_DEFAULT, Mob.SIZE_DEFAULT) {
  override var fireRate: Int = Mob.FIRE_RATE_DEFAULT
  override var damage: Int = Mob.DAMAGE_DEFAULT
  override var speed: Int = Mob.SPEED_DEFAULT
  override var size: Int = Mob.SIZE_DEFAULT
}
