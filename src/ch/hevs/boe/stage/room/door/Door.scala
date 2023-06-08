package ch.hevs.boe.stage.room.door

import ch.hevs.boe.GenStuff.{CollisionGroupNames, CollisionList}
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.gdx2d.lib.GdxGraphics

class Door(position: Position, width: Int, height: Int) extends PhysicalObject(position, width, height){
  CollisionManager.addObjectToGroup(CollisionGroupNames.Door, this, (obj: CollisionList) => {})

}
