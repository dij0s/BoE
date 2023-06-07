package ch.hevs.boe.stage

import ch.hevs.boe.stage.room.{Room, Rooms, SpawnRoom}

import scala.collection.immutable.HashMap
import scala.collection.mutable.ArrayBuffer

object ProceduralGeneration {
	// this method should generate a single
	// stage making use of some magic stuff
	def generateStage(stageDepth: Int): Stage = {
		val stage: Stage = new Stage(new SpawnRoom)
		val generatedRooms: ArrayBuffer[Room] = ArrayBuffer.empty
		
		val scalingFunction = (x: Int) => (1/8 * math.pow(x,2)).toInt
		// stocker la distance minimale depuis le spawn
		val roomsToGenerate: HashMap[Rooms.Value, Int] = HashMap[Room, Int](
			Rooms.ItemRoom -> 1,
			Rooms.BossRoom -> 1,
			Rooms.MobRoom -> (4 + scalingFunction(stageDepth))
		)
		
		roomsToGenerate.foreach(roomType => {
			(0 until roomType._2).foreach(_ => generatedRooms.addOne(Rooms.createRoom(roomType._1)))
		})
		// define doors to go from a room to another
		
	}
}
