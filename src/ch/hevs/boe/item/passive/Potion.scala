package ch.hevs.boe.item.passive

import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.item.PassiveItem
import ch.hevs.boe.physics.Position

object Potion {
  val ITEM_SIZE = 25
}

class Potion(pos: Position, size: Int) extends PassiveItem(pos, Potion.ITEM_SIZE, Potion.ITEM_SIZE) {
  override val isMultiplier: Boolean = false
  override val name: String = "Une potion magique"
  override val description: String = "Augmente vos points de vie"
  override val statEffect: Int = 5

  override def applyItem(target: EntityStatistics): Unit = {
    target.hp = target.hp + this.statEffect
  }
}
