package ch.hevs.boe.entity.mob.boss.predefined

import ch.hevs.boe.entity.mob.{Mob, Mobs}
import ch.hevs.boe.entity.mob.boss.Boss
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position

object TeddyBear extends DefaultEntityStatistics {
	override val FIRE_RATE_DEFAULT: Double = 2
	override val DEFAULT_HP: Int = 20
	override val DAMAGE_DEFAULT: Int = 20
	override val SPEED_DEFAULT: Int = 1
	override val SIZE_DEFAULT: Int = 100
}

class TeddyBear(pos: Position, callbackOnKilled: (Mob) => Unit) extends Boss(pos, Tank.SIZE_DEFAULT, Tank.SIZE_DEFAULT, callbackOnKilled, Mobs.teddyBearSprite) {
	override protected val contactDamage: Int = 1
	override var _hp: Int = Tank.DEFAULT_HP
	override var damage: Int = 2
	override var speed: Int = 2
	override var size: Int = Tank.SIZE_DEFAULT
	override var fireRate: Double = 0.5
}
