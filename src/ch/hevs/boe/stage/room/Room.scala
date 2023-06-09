package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.door.Door
import ch.hevs.boe.utils.Utils
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap

abstract class Room(private val _spriteFilePath: String,
										private var _borders: HashMap[Direction, PhysicalObject] = HashMap.empty,
                    private val _neighbors: HashMap[Direction, Room] = HashMap.empty)
extends Drawable{
	private var roomSprite: Spritesheet = null
	private var doorsSprite: Spritesheet = null

	private def initRoomSprite(sheet: Spritesheet): Unit = roomSprite = sheet
	private def initDoorSprite(sheet: Spritesheet): Unit = doorsSprite = sheet
	private def handleExit(direction: Direction): Room = {
		// TODO: Handle entry to room in given direction by disposing of current sprites and loading next room in stage
		roomSprite.dispose()
		doorsSprite.dispose()
		getNeighborRoom(direction)
	}

	SpritesManager.addSprites(SpritesheetModel(_spriteFilePath, 278, 186), initRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room_door.png", 50, 34), initDoorSprite)

	def borders: HashMap[Direction, PhysicalObject] = _borders
	def borders_= (h: HashMap[Direction, PhysicalObject]): Unit = _borders = h
	def neighbors: HashMap[Direction, Room] = _neighbors
	def addNeighbor(direction: Direction, neighbor: Room): Unit = _neighbors.addOne(direction -> neighbor)
	def hasNeighbors: Boolean = _neighbors.nonEmpty
	def getNeighborRoom(direction: Direction): Room = _neighbors(direction)
	def getNeighborsDirection: Array[Direction] = _neighbors.keys.toArray
	def getEmptyNeighborDirections: Array[Direction] = Directions.values.toArray.diff(_neighbors.keys.toSeq)
	def draw(g: GdxGraphics): Unit = {
		// draws room
		g.draw(roomSprite.sprites(0)(0), 0, 0, g.getScreenWidth, g.getScreenHeight)
		// create physical doors and display them
		_neighbors.foreach(neighbor => {
			val doorPosition: Position = Directions.getDoorPosition(neighbor._1)
			new Door(doorPosition, 100, 62, neighbor._1, doorsSprite, handleExit).draw(g)
		})

		// this down here is needed to show walls hitboxes
		_borders.foreach(border => border._2.draw(g))
	}
}
