package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.door.Door
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import scala.collection.mutable.{HashMap, ListBuffer}

object Room {
	private def getDoorSize(direction: Direction): (Int, Int) = direction match {
		case Directions.TOP | Directions.BOTTOM => (100, 62)
		case _ => (62, 100)
	}

	private def getDoorPosition(direction: Direction): Position = direction match {
		case Directions.TOP => new Position(400, 38)
		case Directions.RIGHT => new Position(800, 269)
		case Directions.BOTTOM => new Position(400, 500)
		case Directions.LEFT => new Position(38, 269)
	}
}

abstract class Room(private val _spriteFilePath: String,
										private var _borders: HashMap[Direction, PhysicalObject] = HashMap.empty,
                    private val _neighbors: HashMap[Direction, Room] = HashMap.empty)
extends Drawable{
	var stageRoomExitCallback: Room => Unit = null

	private var roomSprite: Spritesheet = null
	private var doorsSprite: Spritesheet = null
	private val doorsPhysicalObjects: ListBuffer[Door] = ListBuffer.empty

	private def initRoomSprite(sheet: Spritesheet): Unit = roomSprite = sheet
	private def initDoorSprite(sheet: Spritesheet): Unit = doorsSprite = sheet
	private def handleExit(direction: Direction): Unit = {
		dispose()
		if (stageRoomExitCallback != null) stageRoomExitCallback(getNeighborRoom(direction))
	}

	SpritesManager.addSprites(SpritesheetModel(_spriteFilePath, 278, 186), initRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room_doors.png", 50, 34), initDoorSprite)

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
			val doorPosition: Position = Room.getDoorPosition(neighbor._1)
			val (doorWidth, doorHeight): (Int, Int) = Room.getDoorSize(neighbor._1)
			val doorObject: Door = new Door(doorPosition, doorWidth, doorHeight, neighbor._1, doorsSprite, handleExit)
			doorsPhysicalObjects.addOne(doorObject)
			doorObject.draw(g)
		})

		// this down here is needed to show walls hitboxes
		_borders.foreach(border => border._2.draw(g))
	}
	private def dispose(): Unit = {
		doorsPhysicalObjects.foreach(_.kill())
		_borders.values.foreach(_.kill())
	}
}
