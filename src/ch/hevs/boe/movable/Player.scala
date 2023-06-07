package ch.hevs.boe.movable

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.movable.Player.SIZE
import ch.hevs.boe.movable.statisctics.Statistic
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}

object Player {
  val SIZE: Int = 50
}

class Player(_position: Position = new Position(0, 0)) extends PhysicalObject(_position, Player.SIZE, Player.SIZE) with Statistic {
  private var oldPos: Position = null
  private var playerSprite: Spritesheet = null
  
  private def initSprite(sheet: Spritesheet): Unit = playerSprite = sheet

  CollisionManager.addObjectToGroup(CollisionGroupNames.Player, this, collision)
  
  SpritesManager.addSprites(SpritesheetModel("data/sprites/elijah_temp.png", 28, 33), initSprite)

  override def position_=(newVal: Position): Unit = {
    oldPos = position
    super.position_=(newVal)
  }
  
  override def draw(g: GdxGraphics): Unit = {
    g.draw(playerSprite.sprites(0)(0), _position.x, _position.y, SIZE, SIZE)
    // hitbox
    super.draw(g)
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
