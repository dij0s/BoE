package ch.hevs.boe.item.passive

import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.item.{Items, PassiveItem}
import ch.hevs.boe.physics.Position

object Potion {
  val ITEM_SIZE = 28
}

class Potion extends PassiveItem(new Position(436, 286), Potion.ITEM_SIZE, Potion.ITEM_SIZE, Items.potionSprite) {
  override val isMultiplier: Boolean = false
  override val name: String = "Une potion magique"
  override val description: String = "Augmente vos points de vie"
  override val statEffect: Double = 2

  override def applyItem(target: EntityStatistics): Unit = {
    target.hp += this.statEffect.toInt
  }
}
