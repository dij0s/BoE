package ch.hevs.boe.item.passive

import ch.hevs.boe.entity.statistics.EntityStatistics
import ch.hevs.boe.item.{Items, PassiveItem}
import ch.hevs.boe.physics.Position

object Paracetamol {
  val ITEM_SIZE = 28
}

class Paracetamol extends PassiveItem(new Position(436, 286), Paracetamol.ITEM_SIZE, Paracetamol.ITEM_SIZE, Items.paracetamolSprite) {
  override val isMultiplier: Boolean = true
  override val name: String = "paracetamol"
  override val description: String = "why did my feet grow ?!"
  override val statEffect: Double = 1.25

  override def applyItem(target: EntityStatistics): Unit = target.speed = Math.round(target.speed * this.statEffect).toInt
}
