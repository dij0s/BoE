package ch.hevs.boe.entity.mob.boss

import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position


object Tank extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = ???
  override val DEFAULT_HP: Int = ???
  override val DAMAGE_DEFAULT: Int = ???
  override val SPEED_DEFAULT: Int = ???
  override val SIZE_DEFAULT: Int = 50
}

class Tank(pos: Position, player: Player) extends Boss(pos, Tank.SIZE_DEFAULT, Tank.SIZE_DEFAULT) {
  override protected val contactDamage: Int = 1
  override var damage: Int = 2
  override var speed: Int = 2
  override var size: Int = Tank.SIZE_DEFAULT
  override var fireRate: Double = 0.5


  override def doGameplayTick(): Unit = {
    super.doGameplayTick()
  }
}
