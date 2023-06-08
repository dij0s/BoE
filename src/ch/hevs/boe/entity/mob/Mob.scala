package ch.hevs.boe.entity.mob

import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position

object Mob extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Int = 5
  override val DEFAULT_HP: Int = 2
  override val DAMAGE_DEFAULT: Int = 1
  override val SPEED_DEFAULT: Int = 2
  override val SIZE_DEFAULT: Int = 10
}

abstract class Mob(position: Position, width: Int, height: Int) extends Entity(position, width, height) {
  override def kill(): Unit = {


  }
}
