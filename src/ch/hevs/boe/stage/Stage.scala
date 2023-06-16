package ch.hevs.boe.stage

import ch.hevs.boe.{GameplayManager, zIndex}
import ch.hevs.boe.draw.{DrawManager, Drawable}
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.Room
import ch.hevs.boe.utils.Initiable
import ch.hevs.gdx2d.lib.GdxGraphics

class Stage(private val _spawnRoom: Room, private val _depth: Int) extends Drawable with Initiable{

	// field used to know which room of
	// current stage we should be displaying
	private var drawManagerId: Int = 0
	private var _currentRoom: Room = null
	// we store currentRoom in addition to _spawnRoom
	// so we can later modify the data structure to handle
	// 'LinkedStages' which could allow us to implement
	// some funny stuff like going back to the previous stage
	def currentRoom: Room = _currentRoom
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
		currentRoom = nextRoom
		GameplayManager.player.position = newPosition
		// This is a little hack to avoid the wall collision problem
		// By setting player position two times, we ensure that the old position of the player is the same
		GameplayManager.player.position = newPosition
	}
	def spawnRoom: Room = _spawnRoom
	def depth: Int = _depth

	// TODO : correctly implement following method so we can display a minimap
	def compileGraph(currentRoom: Room = _spawnRoom, lastCheckedDirection: Direction = null): Unit = {
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

	override def draw(g: GdxGraphics): Unit = g.drawString(30, g.getScreenHeight - 30, s"stage ${_depth + 1}", GameplayManager.descriptionFont)

	override protected def _dispose(): Unit = {
		// We need to clear out all the instances correctly
		DrawManager.unsubscribe(drawManagerId)
		this.currentRoom.dispose()
	}

	override protected def _init(): Unit = {
		drawManagerId = DrawManager.subscribe(draw, zIndex.HUD_Z_INDEX)
		this.currentRoom = spawnRoom
	}
}
