package ch.hevs.boe.draw.sprites

import ch.hevs.gdx2d.components.bitmaps.Spritesheet

import scala.collection.mutable.ListBuffer

case class SpritesheetModel(file: String, tileWidth: Int, tileHeight: Int)
case class SpritesheetCreator(model: SpritesheetModel, callback: (Spritesheet) => Unit)

object SpritesManager {
	private val spritesBatch: ListBuffer[SpritesheetCreator] = ListBuffer.empty
//	private val loadedSpritesBatch: ListBuffer[Spritesheet] = ListBuffer.empty
	private var isManagerLoaded: Boolean = false
	
	def addSprites(sheet: SpritesheetModel, cb: (Spritesheet) => Unit): Unit = spritesBatch.addOne(SpritesheetCreator(sheet, cb))
	def init(): Unit = {
		if (!isManagerLoaded) spritesBatch.foreach(spritesCreator => {
			val createdSprite: Spritesheet = new Spritesheet(spritesCreator.model.file, spritesCreator.model.tileWidth, spritesCreator.model.tileHeight)
//			loadedSpritesBatch.addOne(createdSprite)
			spritesCreator.callback(createdSprite)
		})
		isManagerLoaded = true
	}

//	def dispose(): Unit = {
//		loadedSpritesBatch.foreach(_.dispose())
//		isManagerLoaded = false
//	}
}
