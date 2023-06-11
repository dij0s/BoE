package ch.hevs.boe.stage

import ch.hevs.boe.draw.Drawable
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.Room
import ch.hevs.boe.utils.Initiable
import ch.hevs.gdx2d.lib.GdxGraphics

class Stage(private val _spawnRoom: Room, private var _next: Stage = null) extends Drawable with Initiable{

	// field used to know which room of
	// current stage we should be displaying

	private var _currentRoom: Room = null
	private def currentRoom: Room = _currentRoom
	private def currentRoom_= (newRoom: Room): Unit = {
		if(currentRoom != null) {
			currentRoom.dispose()
			currentRoom.stageRoomExitCallback = null
		}
		_currentRoom = newRoom
		if(newRoom != null) {
			currentRoom.init()
			currentRoom.stageRoomExitCallback = handleRoomExit
		}
	}
	def handleRoomExit(nextRoom: Room, newPosition: Position): Unit = {
		println("Changing room ???")
		currentRoom = nextRoom
		// TODO: should modify user position which is given in parameter
	}
	def spawnRoom: Room = _spawnRoom
	def next: Stage = _next
	def next_= (newStage: Stage): Unit = _next = newStage
	def draw(g: GdxGraphics): Unit = _currentRoom.draw(g)

	// TODO : correctly implement following method so we can display a minimap
	def compileGraph(currentRoom: Room = _spawnRoom, lastCheckedDirection: Direction = null): Unit = {
		println("Compiling graph ??")
		println(lastCheckedDirection, currentRoom)
		// room is a leaf if the only neighbor is in the
		// opposite direction of lastCheckedDirection
		val neighborDirections: Array[Direction] = currentRoom.getNeighborsDirection
		if (neighborDirections.length == 1 && neighborDirections.contains(Directions.getOpposite(lastCheckedDirection))) println("this is a leaf room")
		else {
			currentRoom.neighbors.foreach(neighbor => {
				val (neighborDirection, neighborRoom) = neighbor
				// so we don't loop on rooms
				if (neighborDirection != lastCheckedDirection) compileGraph(neighborRoom, neighborDirection)
			})
		}
	}

	override protected def _init(): Unit = {
		this.currentRoom = spawnRoom
	}

	override protected def _dispose(): Unit = {
		// We need to clear out all the instances correctly
		this.currentRoom.dispose()
	}
}
