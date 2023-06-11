package ch.hevs.boe.stage.room

import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.stage.room.predefined.{BossRoom, ItemRoom, MobRoom, SpawnRoom}

object Rooms extends Enumeration {
	val BossRoom, ItemRoom, MobRoom, SpawnRoom = Value
	def createRoom(roomType: Rooms.Value, playerEntity: Player): Room = {
		roomType match {
			case Rooms.BossRoom => new BossRoom(playerEntity)
			case Rooms.ItemRoom => new ItemRoom
			case Rooms.SpawnRoom => new SpawnRoom
			case _ => new MobRoom(playerEntity)
		}
	}
}
