package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.physics.PhysicalObject
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap

abstract class Room(private val _spriteFilePath: String,
										private var _borders: HashMap[Directions.Value, PhysicalObject] = HashMap.empty,
                    private val _neighbor: HashMap[Directions.Value, Room] = HashMap.empty)
extends Drawable{
	private var roomSprite: Spritesheet = null
	
	def borders: HashMap[Directions.Value, PhysicalObject] = _borders
	def borders_= (h: HashMap[Directions.Value, PhysicalObject]): Unit = _borders = h
	def next: Array[Room] = _neighbor.values.toArray
	def addNeighbor(direction: Directions.Value, neighbor: Room): Unit = _neighbor.addOne(direction -> neighbor)
	def hasNeighbors: Boolean = _neighbor.isEmpty
	def draw(g: GdxGraphics): Unit = {
		g.draw(roomSprite.sprites(0)(0), 0, 0, g.getScreenWidth, g.getScreenHeight)
		// this down here is needed to show walls hit boxes
		_borders.foreach(border => border._2.draw(g))
	}
	
	SpritesManager.addSprites(SpritesheetModel(_spriteFilePath, 278, 186), initSprite)
	private def initSprite(sheet: Spritesheet): Unit = roomSprite = sheet
}
