package ch.hevs.boe.item

import ch.hevs.boe.physics.Position
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

abstract class PassiveItem(position: Position,
													 width: Int,
													 height: Int,
													 sprite: Spritesheet) extends Item(position, width, height, sprite) {
	// if isn't a multiplier, then it
	// should be added to player stats
	val isMultiplier: Boolean
	// links the item's effect to the player
	// should be correctly implemented
}
