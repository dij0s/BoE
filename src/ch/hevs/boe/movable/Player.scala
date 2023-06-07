package ch.hevs.boe.movable

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.physics.{CollisionManager, PhysicalObject, Position}
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}
import ch.hevs.boe.movable.statistics.{DefaultStatistics, Statistic, UnitStatistics}

object Player extends DefaultStatistics{
  override val DAMAGE_DEFAULT: Int = 12
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 25
}

class Player extends PhysicalObject(null, Player.SIZE_DEFAULT, Player.SIZE_DEFAULT) with UnitStatistics {

  var damage: Int = Player.DAMAGE_DEFAULT
  var speed: Int = Player.SPEED_DEFAULT
  var size: Int = Player.SIZE_DEFAULT
  var fireRate: Int = 20
  var hp: Int = 5

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
    g.draw(playerSprite.sprites(0)(0), _position.x, _position.y, size, size)
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
