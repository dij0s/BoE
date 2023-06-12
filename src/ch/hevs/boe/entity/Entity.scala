package ch.hevs.boe.entity

import ch.hevs.boe.zIndex
import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.entity.statistics.{DefaultEntityStatistics, EntityStatistics}
import ch.hevs.boe.physics.{PhysicalObject, Position}

object Entity extends DefaultEntityStatistics {
  override val FIRE_RATE_DEFAULT: Double = 0.3
  override val DEFAULT_HP: Int = 10
  override val DAMAGE_DEFAULT: Int = 3
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 25
}

abstract class Entity(pos: Position, width: Int, height: Int) extends PhysicalObject(pos, width, height) with EntityStatistics {
  protected var _hp: Int
  override def hp: Int = _hp
  override def hp_= (newVal: Int): Unit = {
    _hp = newVal
    if(_hp <= 0) {
      this.dispose()
    }
  }

  override def getZIndex = zIndex.MOB_Z_INDEX

  private var oldPos: Position = null
  override def position_=(newPos: Position): Unit = {
    this.oldPos = position.clonePos()
    _position = newPos
  }


  def damageEntity(amount: Int): Unit = {
    this.hp = this.hp - amount
  }

  def restorePreviousPosition(): Unit = {
    if(oldPos != null) {
      this.position = oldPos
    }
  }
}
