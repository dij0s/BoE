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

  def checkCollisions() = {
    val toTriggerArr: HashMap[CollisionObject, CollisionList] = new HashMap[CollisionObject, CollisionList]()
    val groupsClone = groups.clone
    for(name <- groupsClone.keys) {
      val group = groupsClone(name)
      val groupsToCheck = groups.clone()
      groupsToCheck.remove(name)
      for(current <- group.clone) {
        for(toCheck <- groupsToCheck; g <- toCheck._2) {
          if(current.rect.checkCollision(g.rect)) {
            // This means that we have a collision between current and to check
            if(!toTriggerArr.contains(current)) {
              toTriggerArr.addOne(current, new CollisionList())
            }
            if(!toTriggerArr(current).contains(toCheck._1)) {
              toTriggerArr(current).addOne(toCheck._1, new ArrayBuffer[PhysicalObject]())
            }
            toTriggerArr(current)(toCheck._1).addOne(g.rect)
          }
        }
      }
    }
    for(p <- toTriggerArr) {
      p._1.collisionCallback(p._2)
    }
  }

}
