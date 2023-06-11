package ch.hevs.boe.entity.player

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.player.Player.SPRITE_VARIATIONS
import ch.hevs.boe.entity.player.PlayerDirections.PlayerDirections
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.projectile.{PlayerProjectile, Projectile}
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.utils.time.Timeout
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}
import org.lwjgl.opengl.EXTDrawRangeElements


object PlayerDirections extends Enumeration {
  type PlayerDirections = Value
  val TOP, BOTTOM, RIGHT, LEFT, TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT = Value
}

object Player extends DefaultEntityStatistics{
  override val DAMAGE_DEFAULT: Int = 12
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 50
  override val FIRE_RATE_DEFAULT: Double = 1.5
  override val DEFAULT_HP: Int = 5
  val SPRITE_VARIATIONS: Int = 10
}

class Player(pos: Position) extends Entity(pos, Player.SIZE_DEFAULT, Player.SIZE_DEFAULT) {
  var damage: Int = Player.DAMAGE_DEFAULT
  private var _speed: Int = Player.SPEED_DEFAULT
  var size: Int = Player.SIZE_DEFAULT
  var fireRate: Double = Player.FIRE_RATE_DEFAULT

  override def speed = this._speed
  override def speed_=(newVal: Int) = {
    this._speed = newVal
    this.diagonalMovementLength = getDiagonalLength()
  }


  private var spriteMovementIndex: Int = 0

  this._hp = Player.DEFAULT_HP

  private var diagonalMovementLength = getDiagonalLength()
  private var immunityFrames: Boolean = false
  private var onFireCooldown: Boolean = false
  private var playerSprite: Spritesheet = null

  private var currentMovingDirection: PlayerDirections = null


  private def initSprite(sheet: Spritesheet): Unit = playerSprite = sheet


  CollisionManager.addObjectToGroup(CollisionGroupNames.Player, this, collision)

  SpritesManager.addSprites(SpritesheetModel("data/sprites/elijah.png", 28, 43), initSprite)

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
          restorePreviousPosition()
        }
        case CollisionGroupNames.Enemy => {
          // TODO Implement only if player should damage enemy on contact !!!
        }
        case _ => {

        }
      }
    }
  }

  def fire(direction: Direction): Unit = {
    if(onFireCooldown) return
    onFireCooldown = true
    Timeout(Math.round(1000.0 / fireRate).toInt) {onFireCooldown = false}
    val proj = new PlayerProjectile(this, direction)
    proj.damage = damage
  }

  def handleSpriteVariation(): Unit = {
    if ((spriteMovementIndex + 1) < SPRITE_VARIATIONS) spriteMovementIndex += 1
    else spriteMovementIndex = 0
  }


  /**
   * This methods compute the length of a component of the diagonal for the player movement
   * @return The length of a component of the diagonal
   */
  private def getDiagonalLength() : Int = {
    return Math.round(Math.sqrt(Math.pow(speed.toDouble, 2) / 2.0)).toInt
  }

  override def damageEntity(amount: Int): Unit = {
    if(immunityFrames) return
    if(amount == 0) return
    this.hp = hp - amount
    if(amount > 0) {
      immunityFrames = true
      Timeout(500) {immunityFrames = false}
    }
  }

  private def move(oldPos: Position) : Position = {
    val newPos = oldPos.clonePos()
    var moved = true
    currentMovingDirection match {
      case PlayerDirections.TOP => {
        newPos.y -= speed
      }
      case PlayerDirections.BOTTOM => {
        newPos.y += speed
      }
      case PlayerDirections.LEFT => {
        newPos.x -= speed
      }
      case PlayerDirections.RIGHT => {
        newPos.x += speed
      }
      case PlayerDirections.TOP_LEFT => {
        newPos.x -= diagonalMovementLength
        newPos.y -= diagonalMovementLength
      }
      case PlayerDirections.TOP_RIGHT => {
        newPos.x += diagonalMovementLength
        newPos.y -= diagonalMovementLength
      }
      case PlayerDirections.BOTTOM_LEFT => {
        newPos.x -= diagonalMovementLength
        newPos.y += diagonalMovementLength
      }
      case PlayerDirections.BOTTOM_RIGHT => {
        newPos.x += diagonalMovementLength
        newPos.y += diagonalMovementLength
      }
      case _ => {
        moved = false
      }
    }
    if(moved) {
      handleSpriteVariation()
    } else {
      spriteMovementIndex = 0
    }
    return newPos
  }

  override def doGameplayTick() = {


    if(Gdx.input.isKeyPressed(Input.Keys.W)) {
      if(Gdx.input.isKeyPressed(Input.Keys.A)) {
        // TOP LEFT
        this.currentMovingDirection = PlayerDirections.TOP_LEFT
      } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        // TOP RIGHT
        this.currentMovingDirection = PlayerDirections.TOP_RIGHT
      } else {
        // Only top
        this.currentMovingDirection = PlayerDirections.TOP
      }
    } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        // BOTTOM LEFT
        this.currentMovingDirection = PlayerDirections.BOTTOM_LEFT
      } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        // BOTTOM RIGHT
        this.currentMovingDirection = PlayerDirections.BOTTOM_RIGHT
      } else {
        // Only bottom
        this.currentMovingDirection = PlayerDirections.BOTTOM
      }
    } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      this.currentMovingDirection = PlayerDirections.LEFT
    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      this.currentMovingDirection = PlayerDirections.RIGHT
    } else {
      currentMovingDirection = null
    }
  val newPos = move(position)



    if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
      fire(Directions.TOP)
    } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      fire(Directions.RIGHT)
    } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      fire(Directions.BOTTOM)
    } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      fire(Directions.LEFT)
    }
    position = newPos
  }

  override def kill(): Unit = {
    super.kill()
    CollisionManager.removeObjectFromGroup(CollisionGroupNames.Player, this)
    println("YOU LOSE !!!")
    // TODO: Implement death of player
  }
}
