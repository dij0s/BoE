package ch.hevs.boe.item.passive

import ch.hevs.boe.item.PassiveItem
import ch.hevs.boe.movable.statistics.Statistics
import ch.hevs.boe.physics.Position

class TestItem(position: Position, width: Int, height: Int) extends PassiveItem(position, width, height) {
	override val name: String = "TestItem"
	override val description: String = "This test item will increase Elijah's speed"
	override val affectedProperty: Statistics.Value = Statistics.SPEED
	override val statEffect: Double = 1.1
	override val isMultiplier: Boolean = true
}
