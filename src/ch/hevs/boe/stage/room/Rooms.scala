package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.stage.room.predefined.{BossRoom, ItemRoom, MobRoom, SpawnRoom}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

object Rooms extends Enumeration {
	val BossRoom, ItemRoom, MobRoom, SpawnRoom = Value
	
	var roomSprite: Spritesheet = null
	var doorSprite: Spritesheet = null
	var itemRoomSprite: Spritesheet = null
	var doorsSpritesInitiated: Boolean = false
	
	private def initRoomSprite(s: Spritesheet) = roomSprite = s
	private def initDoorSprite(s: Spritesheet) = {
		doorSprite = s
		doorsSpritesInitiated = true
	}
	private def initItemRoomSprite(s: Spritesheet) = itemRoomSprite = s
	
	SpritesManager.addSprites(SpritesheetModel("data/sprites/item_room.png", 278, 186), initItemRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room.png", 278, 186), initRoomSprite)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room_doors.png", 50, 34), initDoorSprite)
	
	def createRoom(roomType: Rooms.Value): Room = {
		roomType match {
			case Rooms.BossRoom => new BossRoom
			case Rooms.ItemRoom => new ItemRoom
			case Rooms.SpawnRoom => new SpawnRoom
			case _ => new MobRoom
		}
	}
}
