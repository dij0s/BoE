package ch.hevs.boe.movable

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.movable.statistics.{DefaultStatistics, Statistic}
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}

import scala.collection.mutable.{ArrayBuffer, HashMap}

object Player extends DefaultStatistics{
  override val DAMAGE_DEFAULT: Int = 12
}

class Player extends PhysicalObject(null, Player.DAMAGE_SIZE, Player.size) with Statistic {

  var damage: Int = Player.DAMAGE_DEFAULT
  var speed: Int = Player.speed
  var size: Int = Player.size


  def this(_position: Position = new Position(0, 0)) = {
    this(_position, this.size, this.size)
  }

  private var oldPos: Position = null

  CollisionManager.addObjectToGroup(CollisionGroupNames.Player, this, collision)

  override def position_=(newVal: Position): Unit = {
    oldPos = position
    super.position_=(newVal)
  }



  def collision(obj: CollisionList) = {
    for(v <- obj) {
      v._1 match {
        case CollisionGroupNames.Wall => {
          if (oldPos != null) {
            position = oldPos
          } else {
            println("Spawned in a wall !")
          }
        }
        case CollisionGroupNames.Enemy => {

        }
        case CollisionGroupNames.EnemyProjectile => {

        }
      }
    }
  }


  def doGameplayTick() = {
    var newX = position.x
    var newY = position.y
    if(Gdx.input.isKeyPressed(Input.Keys.W)) {
      newY -= speed
    } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      newX -= speed
    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      newY += speed
    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      newX += speed
    }
    position = new Position(newX, newY)
  }
}
