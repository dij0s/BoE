package ch.hevs.boe.item

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.movable.statistics.UnitStatistics
import ch.hevs.boe.physics.{CollisionManager, Position}

protected abstract class PassiveItem(position: Position,
                                     width: Int,
                                     height: Int) extends Item(position, width, height) {
	// if isn't a multiplier, then it
	// should be added to player stats
	val isMultiplier: Boolean
	// links the item's effect to the player
	// should be correctly implemented
<<<<<<< HEAD
	def triggerLink: Unit = {
		if (isMultiplier) null // should impact player's speed
		else null // adds player's property to current stats
	}
=======
>>>>>>> feature/stats
}
