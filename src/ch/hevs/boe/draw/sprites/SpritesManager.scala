package ch.hevs.boe.draw.sprites

import ch.hevs.boe.utils.Initiable
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.collection.mutable.ListBuffer

case class SpritesheetModel(file: String, tileWidth: Int, tileHeight: Int)
case class SpritesheetCreator(model: SpritesheetModel, callback: (Spritesheet) => Unit, mustDispose: Boolean)

object SpritesManager extends Initiable {
	private val spritesBatch: ListBuffer[SpritesheetCreator] = ListBuffer.empty

	def addSprites(sheet: SpritesheetModel, cb: (Spritesheet) => Unit, mustDispose: Boolean = true): Unit = {
		if(!initiated) {
			spritesBatch.addOne(SpritesheetCreator(sheet, cb, mustDispose))
		} else {
			val createdSprite: Spritesheet = new Spritesheet(sheet.file, sheet.tileWidth, sheet.tileHeight)
			cb(createdSprite)
		}
	}
	def _init(): Unit = {
		spritesBatch.foreach(spritesCreator => {
			val createdSprite: Spritesheet = new Spritesheet(spritesCreator.model.file, spritesCreator.model.tileWidth, spritesCreator.model.tileHeight)
			spritesCreator.callback(createdSprite)
		})
	}

	override protected def _dispose(): Unit = {
		println("You should never dispose the sprite manager !!!")
	}
}
