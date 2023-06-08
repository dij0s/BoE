package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap

abstract class Room(private val _spriteFilePath: String,
										private var _borders: HashMap[Direction, PhysicalObject] = HashMap.empty,
                    private val _neighbor: HashMap[Direction, Room] = HashMap.empty)
extends Drawable{
	// TODO: display exit doors, know which one I collide with and then handle exit

	private var roomSprite: Spritesheet = null
	private var doorsSprite: Spritesheet = null

	def borders: HashMap[Direction, PhysicalObject] = _borders
	def borders_= (h: HashMap[Direction, PhysicalObject]): Unit = _borders = h
	def neighbors: HashMap[Direction, Room] = _neighbor
	def addNeighbor(direction: Direction, neighbor: Room): Unit = _neighbor.addOne(direction -> neighbor)
	def hasNeighbors: Boolean = _neighbor.nonEmpty
	def getNeighborRoom(direction: Direction): Room = _neighbor(direction)
	def getNeighborsDirection: Array[Direction] = _neighbor.keys.toArray
	def getEmptyNeighborDirections: Array[Direction] = Directions.values.toArray.diff(_neighbor.keys.toSeq)
	def draw(g: GdxGraphics): Unit = {
		g.draw(roomSprite.sprites(0)(0), 0, 0, g.getScreenWidth, g.getScreenHeight)

		val doorPosition: Position = new Position(400, 500)
		g.draw(doorsSprite.sprites(0)(0), doorPosition.x, doorPosition.y, 100, 62)

		// this down here is needed to show walls hitboxes
		_borders.foreach(border => border._2.draw(g))
	}

	SpritesManager.addSprites(SpritesheetModel(_spriteFilePath, 278, 186), initRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room_door.png", 50, 34), initDoorsSprite)
	private def initRoomSprite(sheet: Spritesheet): Unit = roomSprite = sheet
	private def initDoorsSprite(sheet: Spritesheet): Unit = doorsSprite = sheet
}
