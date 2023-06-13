package ch.hevs.boe.item.passive

import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.item.{Items, PassiveItem}
import ch.hevs.boe.physics.Position

object Booster {
  val ITEM_SIZE = 28
}

class Booster extends PassiveItem(new Position(436, 286), Sarm.ITEM_SIZE, Sarm.ITEM_SIZE, Items.boosterSprite) {
  override val isMultiplier: Boolean = true
  override val name: String = "A strange pill"
  override val description: String = "JE COURS VIIIIIITE"
  override val statEffect: Int = 2

  override def applyItem(target: EntityStatistics): Unit = target.speed = target.speed * this.statEffect
}
