package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.{DrawManager, Drawable}
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.mob.Mob
import ch.hevs.boe.physics.{PhysicalObject, Position}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.door.Door
import ch.hevs.boe.utils.Initiable
import ch.hevs.boe.zIndex
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, HashMap, ListBuffer}

object Room {
	var roomSprite: Spritesheet = null
	var doorSprite: Spritesheet = null
	var itemRoomSprite: Spritesheet = null
	var doorsSpritesInitiated: Boolean = false

	def initRoomSprite(s: Spritesheet) = roomSprite = s
	def initDoorSprite(s: Spritesheet) = {
		doorSprite = s
		doorsSpritesInitiated = true
	}
	def initItemRoomSprite(s: Spritesheet) = itemRoomSprite = s


	SpritesManager.addSprites(SpritesheetModel("data/sprites/item_room.png", 278, 186), initItemRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room.png", 278, 186), initRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room_doors.png", 50, 34), initDoorSprite)




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
		direction match {
			case Directions.TOP => new Position(435, 440)
			case Directions.RIGHT => new Position(110, 269)
			case Directions.BOTTOM => new Position(425, 110)
			case Directions.LEFT => new Position(740, 275)
		}
	}
}

abstract class Room(private val _sprites:Spritesheet = Room.roomSprite,
											private var _borders: HashMap[Direction, PhysicalObject] = HashMap(Directions.LEFT -> new Wall(new Position(0, 0), 100, 600, Directions.LEFT),
											Directions.BOTTOM -> new Wall(new Position(100, 500), 700, 100, Directions.BOTTOM),
											Directions.RIGHT -> new Wall(new Position(800, 0), 100, 600, Directions.RIGHT),
											Directions.TOP -> new Wall(new Position(100, 0), 700, 100, Directions.TOP)),
                    private val _neighbors: HashMap[Direction, Room] = HashMap.empty)
extends Drawable with Initiable {
	var stageRoomExitCallback: (Room, Position) => Unit = null

	private val subscribers: mutable.HashMap[Int, () => Unit] = new mutable.HashMap[Int, () => Unit]()
	private var subsriberIndex: Int = 0
	private val doorsPhysicalObjects: ListBuffer[Door] = ListBuffer.empty

	private var drawManagerId: Int = -1

	protected val mobs: ListBuffer[Mob] = ListBuffer.empty
	private def handleExit(direction: Direction): Unit = if (mobs.isEmpty) {
		dispose()
		val playerPosition: Position = Room.getPlayerPositionOnExit(direction)
		if (stageRoomExitCallback != null) stageRoomExitCallback(getNeighborRoom(direction), playerPosition)
	}


	def borders: HashMap[Direction, PhysicalObject] = _borders
	def borders_= (newBorders: HashMap[Direction, PhysicalObject]): Unit = _borders = newBorders
	def neighbors: HashMap[Direction, Room] = _neighbors
	def addNeighbor(direction: Direction, neighbor: Room): Unit = {
		_neighbors.addOne(direction -> neighbor)
		refreshDoors()
	}
	def hasNeighbors: Boolean = _neighbors.nonEmpty
	def getNeighborRoom(direction: Direction): Room = _neighbors(direction)
	def getNeighborsDirection: Array[Direction] = _neighbors.keys.toArray
	def getEmptyNeighborDirections: Array[Direction] = Directions.values.toArray.diff(_neighbors.keys.toSeq)

	def onDispose(cb: () => Unit): Int = {
		val oldIndex = subsriberIndex
		subscribers.addOne(oldIndex, cb)
		subsriberIndex += 1
		return oldIndex
	}

	def offDispose(i: Int): Unit = {
		if(subscribers.contains(i)) {
			subscribers.remove(i)
		} else {
			println("Couldn't find subscriber in room dispose subscribers")
		}
	}

	private def refreshDoors(): Unit = {
		doorsPhysicalObjects.foreach(_.dispose())
		doorsPhysicalObjects.clear()
		_neighbors.foreach(neighbor => {
			val doorPosition: Position = Room.getDoorPosition(neighbor._1)
			val (doorWidth, doorHeight): (Int, Int) = Room.getDoorSize(neighbor._1)
			val doorObject: Door = new Door(doorPosition, doorWidth, doorHeight, neighbor._1, Room.doorSprite, handleExit)
			// We only want to init the doors right away
			// if the room's which is creating the doors is initiated.
			// Else, the doors will be initiated in the room _init method
			if(this.initiated) doorObject.init()
			doorsPhysicalObjects.addOne(doorObject)
		})
	}

	def draw(g: GdxGraphics): Unit = {
		// draws room
		g.draw(_sprites.sprites(0)(0), 0, 0, g.getScreenWidth, g.getScreenHeight)
		doorsPhysicalObjects.foreach(_.closed =  mobs.nonEmpty)
	}



	override protected def _init(): Unit = {
		drawManagerId = DrawManager.subscribe(draw, zIndex.BACKGROUND_Z_INDEX)
		borders.values.foreach(_.init())
		doorsPhysicalObjects.foreach(_.init())
		mobs.foreach(_.init())
	}
	
	override protected def _dispose(): Unit = {
		DrawManager.unsubscribe(drawManagerId)
		_borders.values.foreach(_.dispose())
		doorsPhysicalObjects.foreach(_.dispose())
		mobs.foreach(_.dispose())
		// Triggering all dispose callback
//		for(s <- subscribers.clone.values) {
//			s()
//		}
		subscribers.clone.values.foreach(_())
	}
}
