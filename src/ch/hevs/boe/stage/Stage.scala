package ch.hevs.boe.stage

import ch.hevs.boe.stage.room.Room

class Stage(private val _spawnRoom: Room, private var _next: Stage = null) {
	def spawnRoom: Room = _spawnRoom
	//	def buildGraphFromSpawnRoom ?
	def next: Stage = _next
	def next_= (newStage: Stage): Unit = _next = newStage
}
