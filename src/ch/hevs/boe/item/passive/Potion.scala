package ch.hevs.boe.item.passive

import ch.hevs.boe.item.PassiveItem
import ch.hevs.boe.movable.statistics.UnitStatistics
import ch.hevs.boe.physics.Position

class Potion(pos: Position, size: Int) extends PassiveItem(pos, size, size) {
  override val isMultiplier: Boolean = false
  override val name: String = "Une potion magique"
  override val description: String = "Augmente vos points de vie"
  override val statEffect: Int = 5

  override def applyItem(target: UnitStatistics): Unit = {
    target.hp = target.hp + this.statEffect
  }
}
