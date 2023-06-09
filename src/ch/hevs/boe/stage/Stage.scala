package ch.hevs.boe.stage

import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.Room
import ch.hevs.gdx2d.lib.GdxGraphics

class Stage(private val _spawnRoom: Room, private val _changeRoom: () => Room, private var _next: Stage = null) {
	// field used to know which room of
	// current stage we should be displaying
	private val _currentRoom: Room = _changeRoom.apply()
	def spawnRoom: Room = _spawnRoom
	def next: Stage = _next
	def next_= (newStage: Stage): Unit = _next = newStage
	// TODO : correctly implement following method so we can display a minimap
	def draw(g: GdxGraphics): Unit = _currentRoom.draw(g)
	def compileGraph(currentRoom: Room = _spawnRoom, lastCheckedDirection: Direction = null): Unit = {
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
}
