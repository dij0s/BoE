package ch.hevs.boe.physics

import ch.hevs.boe.physics.Types.{CollisionCallback, CollisionGroup}

import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
object Types {
  type CollisionCallback = (ArrayBuffer[PhysicObject]) => Unit

  type CollisionGroup = HashMap[String, ArrayBuffer[CollisionObject]]
}


case class CollisionObject(rect: PhysicObject, collisionCallback: CollisionCallback)

class CollisionManager {

  var groups: CollisionGroup = new CollisionGroup()


  def addObjectToGroup(groupName: String, obj:PhysicObject, cb: CollisionCallback) = {
    if(!groups.contains(groupName)) {
      groups.addOne(groupName, new ArrayBuffer[CollisionObject]())
    }
    groups(groupName).addOne(new CollisionObject(obj, cb))
  }

  def checkCollisions() = {
    val toTriggerArr: HashMap[CollisionObject, ArrayBuffer[PhysicObject]] = new HashMap[CollisionObject, ArrayBuffer[PhysicObject]]()
    for(name <- groups.keys) {
      val group = groups(name)
      val groupsToCheck = groups.clone()
      groupsToCheck.remove(name)
      for(current <- group) {
        for(toCheck <- groupsToCheck.values; g <- toCheck) {
          if(current.rect.checkCollision(g.rect)) {
            // This means that we have a collision between current and to check
            if(!toTriggerArr.contains(current)) {
              toTriggerArr.addOne(current, new ArrayBuffer[PhysicObject]())
            }
            toTriggerArr(current).addOne(g.rect)
          }
        }
      }
    }
    for(p <- toTriggerArr) {
      p._1.collisionCallback(p._2)
    }
  }

}
