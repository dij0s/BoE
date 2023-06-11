package ch.hevs.boe.projectile

import ch.hevs.boe.GenStuff.CollisionGroupNames
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction

class PlayerProjectile(player: Entity, direction: Direction) extends Projectile(player) {

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.PlayerProjectile
  override def getNewCoordinates(currentPos: Position): Position = {
    var newX = position.x
    var newY = position.y

    direction match {
      case Directions.TOP => {
        newY = newY - speed
      }
      case Directions.BOTTOM => {
        newY = newY + speed
      }
      case Directions.RIGHT => {
        newX = newX + speed
      }
      case Directions.LEFT => {
        newX = newX - speed
      }
      case _ => {
        println("You shouldn't be here !!!")
      }
    }
    return new Position(newX, newY)
  }
}
