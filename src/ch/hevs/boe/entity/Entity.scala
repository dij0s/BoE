package ch.hevs.boe.entity

import ch.hevs.boe.entity.statistics.{DefaultEntityStatistics, EntityStatistics}
import ch.hevs.boe.physics.{PhysicalObject, Position}

object Entity extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Int = 2
  override val DEFAULT_HP: Int = 10
  override val DAMAGE_DEFAULT: Int = 3
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 25
}

abstract class Entity(pos: Position, width: Int, height: Int) extends PhysicalObject(pos, width, height) with EntityStatistics {
  protected var _hp: Int = Entity.DEFAULT_HP

  override def hp: Int = _hp
  override def hp_= (newVal: Int) = {
    _hp = newVal
    if(_hp == 0) {
      this.kill()
    }
  }


  def kill(): Unit
}
