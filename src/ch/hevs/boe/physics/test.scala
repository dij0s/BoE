package ch.hevs.boe.physics

import ch.hevs.boe.movable.Player

import scala.collection.mutable.ArrayBuffer

object test extends App {
  val pos1 = new Position(0, 0)
  var test: Player = new Player(pos1)
  var test2: Player = new Player(new Position(-50, -50))

  //println(test.checkCollision(test2))

  val rect1: Player = new Player(new Position(0, 0))
  var rect2: Player = new Player(new Position(50, 50))
  val manager: CollisionManager = new CollisionManager()
  manager.addObjectToGroup("1", rect1, (rects: ArrayBuffer[PhysicObject]) => {
    println("Got contact with " + rects.length + " rectangles")
  })
  manager.addObjectToGroup("2", rect2, (rect: ArrayBuffer[PhysicObject]) => {
    println("Got collision from group 2 with " + rect.length + " rectangles")
  })
  manager.checkCollisions()
}
