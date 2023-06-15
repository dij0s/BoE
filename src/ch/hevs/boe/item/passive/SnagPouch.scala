package ch.hevs.boe.item.passive

import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.item.{Items, PassiveItem}
import ch.hevs.boe.physics.Position

object SnagPouch {
  val ITEM_SIZE = 28
}

class SnagPouch extends PassiveItem(new Position(436, 286), SnagPouch.ITEM_SIZE, SnagPouch.ITEM_SIZE, Items.snagPouchSprite) {
  override val isMultiplier: Boolean = false
  override val name: String = "snag pouch"
  override val description: String = "I'm AB+ sadly :("
  override val statEffect: Double = 2

  override def applyItem(target: EntityStatistics): Unit = target.hp += this.statEffect.toInt
}
