package ch.hevs.boe.utils.animations

import ch.hevs.boe.utils.Initiable
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

trait Animation extends Initiable {
	def start(sheet: Spritesheet): Unit
}
