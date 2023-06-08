package ch.hevs.boe.entity.player

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.player.Player.SPRITE_VARIATIONS
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.projectile.Projectile
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}


object Player extends DefaultEntityStatistics{
  override val DAMAGE_DEFAULT: Int = 12
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 50
  override val FIRE_RATE_DEFAULT: Int = 2
  override val DEFAULT_HP: Int = 5
  val SPRITE_VARIATIONS: Int = 10
}

class Player(pos: Position) extends Entity(pos, Player.SIZE_DEFAULT, Player.SIZE_DEFAULT) {
  var damage: Int = Player.DAMAGE_DEFAULT
  var speed: Int = Player.SPEED_DEFAULT
  var size: Int = Player.SIZE_DEFAULT
  var fireRate: Int = 20
  private var spriteMovementIndex: Int = 0

  // TODO: Maybe find a better way to do that
  this._hp = Player.DEFAULT_HP

  private var oldPos: Position = null
  private var playerSprite: Spritesheet = null
  
  private def initSprite(sheet: Spritesheet): Unit = playerSprite = sheet

  CollisionManager.addObjectToGroup(CollisionGroupNames.Player, this, collision)
  
  SpritesManager.addSprites(SpritesheetModel("data/sprites/elijah.png", 28, 43), initSprite)

  override def position_=(newVal: Position): Unit = {
    oldPos = position
    super.position_=(newVal)
  }
  
  override def draw(g: GdxGraphics): Unit = {
    val updatedY: Int = g.getScreenHeight - _position.y - size
    g.draw(playerSprite.sprites(0)(spriteMovementIndex), _position.x, updatedY, size, size)
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

  def fire(direction: Direction) = {
    val proj = new Projectile(this.position, direction, true)
    proj.damage = damage
  }

  def handleSpriteVariation(): Unit = {
    if ((spriteMovementIndex + 1) < SPRITE_VARIATIONS) spriteMovementIndex += 1
    else spriteMovementIndex = 0
  }
  
  def doGameplayTick: Unit = {
    var newX = position.x
    var newY = position.y


    if(Gdx.input.isKeyPressed(Input.Keys.W)) {
      newY -= speed
      handleSpriteVariation()
    } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      newX -= speed
      handleSpriteVariation()
    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      newY += speed
      handleSpriteVariation()
    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      newX += speed
      handleSpriteVariation()
    } else spriteMovementIndex = 0


    if(Gdx.input.isKeyPressed(Input.Keys.UP)) {

    } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {

    } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {

    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {

    } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

    }
    position = new Position(newX, newY)
  }

  override def kill(): Unit = {
    // TODO: Implement death of player
  }
}
