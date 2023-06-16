package ch.hevs.boe.physics

import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer
import ch.hevs.boe.GenStuff._

import scala.collection.mutable

object CollisionManager {
  val groups: CollisionGroup = new CollisionGroup()

  def addObjectToGroup(groupName: CollisionGroupNames, obj:PhysicalObject, cb: CollisionCallback) = {
    if(!groups.contains(groupName)) {
      groups.addOne(groupName, new ArrayBuffer[CollisionObject]())
    }
    groups(groupName).addOne(new CollisionObject(obj, cb))
  }

  def removeObjectFromGroup(groupName: CollisionGroupNames, obj: PhysicalObject): Unit = {
    if(groups.contains(groupName)) {
      val group = groups(groupName)
      val clone = group.clone()
      for(i <- 0 until clone.length) {
        val current = clone(i)
        if(current.rect == obj) {
          group.remove(i)
          return
        }
      }
      println("Trying to remove an object that was not in the group")
    } else {
      println("Trying to remove an object from a non existent group")
    }
  }

  /**
   * Check the collision between all subscribed physical objects
   */
  def checkCollisions() = {
    // This array is here to collect all objects in collision with an object before trigerring the callback
    val toTriggerArr: HashMap[CollisionObject, CollisionList] = new HashMap[CollisionObject, CollisionList]()
    val groupsClone = groups.clone
    // Looping through all groups
    for(name <- groupsClone.keys) {
      // Current group
      val group = groupsClone(name)

      // Cloning all groups, removing current group to prevent collision between objects of same group
      val groupsToCheck = groups.clone
      groupsToCheck.remove(name)
      // Looping through each physical object/callback pair of current group
      for(current <- group.clone) {
        // Looping through each object of every group except the current group
        for(toCheck <- groupsToCheck; g <- toCheck._2) {
          if(current.rect.checkCollision(g.rect)) {
            // This means that we have a collision between current and to check

            // If there is not the current object in the trigger array
            if(!toTriggerArr.contains(current)) {
              toTriggerArr.addOne(current, new CollisionList())
            }
            // If there is not the current group in the trigger array
            if(!toTriggerArr(current).contains(toCheck._1)) {
              toTriggerArr(current).addOne(toCheck._1, new ArrayBuffer[PhysicalObject]())
            }
            // We add the object in which it is in collision in the trigger array
            toTriggerArr(current)(toCheck._1).addOne(g.rect)
          }
        }
      }
    }
    // Triggering all callbacks
    for(p <- toTriggerArr) {
      p._1.collisionCallback(p._2)
    }
  }

}
