package ch.hevs.boe.draw.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.collection.mutable.ListBuffer

object SpritesManager {
	private val spritesBatch: ListBuffer[Spritesheet] = ListBuffer.empty
	
	def addSprites(sheet: Spritesheet): Unit = spritesBatch.addOne(sheet)
}
