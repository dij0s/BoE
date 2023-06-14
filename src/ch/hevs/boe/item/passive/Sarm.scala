package ch.hevs.boe.item.passive

import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.item.{Items, PassiveItem}
import ch.hevs.boe.physics.Position

object Sarm {
  val ITEM_SIZE = 28
}

class Sarm extends PassiveItem(new Position(436, 286), Sarm.ITEM_SIZE, Sarm.ITEM_SIZE, Items.sarmSprite) {
  override val isMultiplier: Boolean = true
  override val name: String = "A syringe of SARMS"
  override val description: String = "I'm on steroids !"
  override val statEffect: Double = 1.4

  override def applyItem(target: EntityStatistics): Unit = target.fireRate = target.fireRate * statEffect
}
