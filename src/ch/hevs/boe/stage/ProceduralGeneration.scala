package ch.hevs.boe.stage

import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.stage.room.predefined.{BossRoom, ItemRoom, MobRoom, SpawnRoom}
import ch.hevs.boe.stage.room.{Room, Rooms}

import scala.collection.immutable.HashMap
import scala.util.Random

object ProceduralGeneration {
	private val leafMobRoomFactor: Float = 0.7f
	// this method should generate a single
	def generateStage(stageDepth: Int = 0): Stage = {
		val spawnRoom: Room = new SpawnRoom

		val scalingFunction = (x: Int) => 1/8 * math.pow(x,2)
		// stocker la distance minimale depuis le spawn
		val leafRoomsToGenerate: HashMap[Rooms.Value, Int] = HashMap[Rooms.Value, Int](
			Rooms.BossRoom -> (3 + leafMobRoomFactor * scalingFunction(stageDepth)).toInt,
			Rooms.MobRoom -> (4 + scalingFunction(stageDepth)).toInt,
			Rooms.ItemRoom -> (1 + scalingFunction(stageDepth)).toInt
		)

		leafRoomsToGenerate.foreach(leafRoom => {
			val (leafRoomType, leafRoomDistance) = leafRoom

			var directionToAddRoom: Direction = null
			var currentRoom: Room = spawnRoom

			// 'to' so we also handle leaf room in here
			(0 to leafRoomDistance).foreach(i => {
				// add next room in random empty direction
				val emptyDirections: Array[Direction] = currentRoom.getEmptyNeighborDirections
				directionToAddRoom = emptyDirections(Random.nextInt(emptyDirections.length))
				val newRoom: Room = if (i < leafRoomDistance) new MobRoom else Rooms.createRoom(leafRoomType)
				newRoom.addNeighbor(Directions.getOpposite(directionToAddRoom), currentRoom)
				currentRoom.addNeighbor(directionToAddRoom, newRoom)
				// current room now is freshly created room
				currentRoom = newRoom
			})
		})

		// TODO: create loops in graph ?
		
		new Stage(spawnRoom, stageDepth)
		//new Stage(new BossRoom, stageDepth)
	}
}
