package ch.hevs.boe.item.passive

import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.item.PassiveItem
import ch.hevs.boe.physics.Position

object Gun {
  val ITEM_SIZE = 25
}

class Gun(pos: Position) extends PassiveItem(pos, Gun.ITEM_SIZE, Gun.ITEM_SIZE){
  override val isMultiplier: Boolean = true
  override val name: String = "A gun"
  override val description: String = "May be practical in case of a global conflict"
  override val statEffect: Int = 2

  override def applyItem(target: EntityStatistics): Unit = {
    target.damage = target.damage * this.statEffect
  }
}
