package ch.hevs.boe.item

import ch.hevs.boe.physics.{Position}

import scala.collection.mutable.ArrayBuffer

protected abstract class PassiveItem(position: Position,
                                     width: Int,
                                     height: Int) extends Item(position, width, height) {
	// if isn't a multiplier, then it
	// should be added to player stats
	val isMultiplier: Boolean
	// links the item's effect to the player
	// should be correctly implemented
}
