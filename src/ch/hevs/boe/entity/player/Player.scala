package ch.hevs.boe.entity.player

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.projectile.Projectile
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}


object Player extends DefaultEntityStatistics{
  override val DAMAGE_DEFAULT: Int = 12
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 25
  override val FIRE_RATE_DEFAULT: Int = 2
  override val DEFAULT_HP: Int = 5
}

class Player(pos: Position) extends Entity(pos, Player.SIZE_DEFAULT, Player.SIZE_DEFAULT) {
  var damage: Int = Player.DAMAGE_DEFAULT
  var speed: Int = Player.SPEED_DEFAULT
  var size: Int = Player.SIZE_DEFAULT
  var fireRate: Int = 20

  // TODO: Maybe find a better way to do that
  this._hp = Player.DEFAULT_HP

  private var oldPos: Position = null
  private var playerSprite: Spritesheet = null


  // TEMP
  var fireKeyDown: Boolean = false
  private def initSprite(sheet: Spritesheet): Unit = playerSprite = sheet


  DrawManager.subscribe(draw)
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
        case _ => {

        }
      }
    }
  }

  def fire(direction: Direction) = {
    val projPos: Position = new Position(position.x + width / 2, position.y + height / 2)
    val proj = new Projectile(projPos, direction, true)
    proj.damage = damage
  }
  
  override def doGameplayTick() = {
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
    if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
      fire(Directions.TOP)
    } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      fire(Directions.RIGHT)
    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      fire(Directions.BOTTOM)
    } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      fire(Directions.LEFT)
    }
    position = new Position(newX, newY)
  }

  override def kill(): Unit = {

    // TODO: Implement death of player
  }
}
