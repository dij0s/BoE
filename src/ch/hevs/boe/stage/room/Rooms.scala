package ch.hevs.boe.stage.room

import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.stage.room.predefined.{BossRoom, ItemRoom, MobRoom, SpawnRoom}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

object Rooms extends Enumeration {
	val BossRoom, ItemRoom, MobRoom, SpawnRoom = Value
	
	var mobRoomSprite: Spritesheet = null
	var spawnRoomSprite: Spritesheet = null
	var bossRoomSprite: Spritesheet = null
	var itemRoomSprite: Spritesheet = null
	
	var doorsSpritesInitiated: Boolean = false
	var doorSprite: Spritesheet = null
	
	private def initDoorSprite(s: Spritesheet): Unit = {
		doorSprite = s
		doorsSpritesInitiated = true
	}
	
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room_spawn.png", 278, 186), (sheet) => spawnRoomSprite = sheet)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/cave_room.png", 278, 186), (sheet) => mobRoomSprite = sheet)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/item_room.png", 278, 186), (sheet) => itemRoomSprite = sheet)
	SpritesManager.addSprites(SpritesheetModel("data/sprites/boss_room.png", 278, 186), (sheet) => bossRoomSprite = sheet)
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
