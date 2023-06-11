package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.Room.getPlayerPositionOnExit
import ch.hevs.boe.stage.room.door.Door
import ch.hevs.boe.utils.Initiable
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

	private def getPlayerPositionOnExit(direction: Direction): Position = {
		println(direction)
		direction match {
			case Directions.TOP => new Position(425, 450)
			case Directions.RIGHT => new Position(100, 275)
			//		case Directions.BOTTOM => new Position(425, 100)
			//		case Directions.LEFT => new Position(750, 275)
			case _ => new Position(0, 0)
		}
	}
}

abstract class Room(private val _spriteFilePath: String,
										private var _borders: HashMap[Direction, PhysicalObject] = HashMap.empty,
                    private val _neighbors: HashMap[Direction, Room] = HashMap.empty)
extends Drawable with Initiable {
	var stageRoomExitCallback: (Room, Position) => Unit = null

	private var roomSprite: Spritesheet = null
	private var doorsSprite: Spritesheet = null
	private val doorsPhysicalObjects: ListBuffer[Door] = ListBuffer.empty
	private var doorsSpritesInitiated: Boolean = false

	protected val mobs: ListBuffer[Mob] = ListBuffer.empty

	private def initRoomSprite(sheet: Spritesheet): Unit = roomSprite = sheet
	private def initDoorSprite(sheet: Spritesheet): Unit = {
		doorsSprite = sheet
		doorsSpritesInitiated = true
		refreshDoors()
	}
	private def handleExit(direction: Direction): Unit = if (mobs.isEmpty) {
		dispose()
		val playerPosition: Position = getPlayerPositionOnExit(direction)
		if (stageRoomExitCallback != null) stageRoomExitCallback(getNeighborRoom(direction), playerPosition)
	}

	SpritesManager.addSprites(SpritesheetModel(_spriteFilePath, 278, 186), initRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room_doors.png", 50, 34), initDoorSprite)

	def borders: HashMap[Direction, PhysicalObject] = _borders
	def borders_= (newBorders: HashMap[Direction, PhysicalObject]): Unit = _borders = newBorders
	def neighbors: HashMap[Direction, Room] = _neighbors
	def addNeighbor(direction: Direction, neighbor: Room): Unit = {
		_neighbors.addOne(direction -> neighbor)
	}
	def hasNeighbors: Boolean = _neighbors.nonEmpty
	def getNeighborRoom(direction: Direction): Room = _neighbors(direction)
	def getNeighborsDirection: Array[Direction] = _neighbors.keys.toArray
	def getEmptyNeighborDirections: Array[Direction] = Directions.values.toArray.diff(_neighbors.keys.toSeq)

	override protected def _init(): Unit = {
		for(d <- doorsPhysicalObjects) {
			d.init()
		}
		for(b <- borders.values) {
			b.init()
		}
	}

	private def refreshDoors(): Unit = {
		if(!doorsSpritesInitiated) return
		for(i <- doorsPhysicalObjects) {
			i.dispose()
		}
		doorsPhysicalObjects.clear()
		_neighbors.foreach(neighbor => {
			val doorPosition: Position = Room.getDoorPosition(neighbor._1)
			val (doorWidth, doorHeight): (Int, Int) = Room.getDoorSize(neighbor._1)
			val doorObject: Door = new Door(doorPosition, doorWidth, doorHeight, neighbor._1, doorsSprite, handleExit)
			doorObject.init()
			doorsPhysicalObjects.addOne(doorObject)
		})
	}

	def draw(g: GdxGraphics): Unit = {
		// draws room
		g.draw(roomSprite.sprites(0)(0), 0, 0, g.getScreenWidth, g.getScreenHeight)
	}

	override protected def _dispose(): Unit = {
		doorsPhysicalObjects.foreach(_.dispose())
		_borders.values.foreach(_.dispose())
	}
}
