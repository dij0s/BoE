package ch.hevs.boe.entity.player

import ch.hevs.boe.GameplayManager
import ch.hevs.boe.GenStuff.CollisionGroupNames.CollisionGroupNames
import ch.hevs.boe.GenStuff._
import ch.hevs.boe.draw.sprites.{SpritesManager, SpritesheetModel}
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.player.Player.SPRITE_VARIATIONS
import ch.hevs.boe.entity.player.PlayerDirections.PlayerDirections
import ch.hevs.boe.entity.statistics.DefaultEntityStatistics
import ch.hevs.boe.physics.Position
import ch.hevs.boe.projectile.predefined.player.PlayerProjectile
import ch.hevs.boe.stage.Directions
import ch.hevs.boe.stage.Directions.Direction
import ch.hevs.boe.utils.time.Timer
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}

object PlayerDirections extends Enumeration {
  type PlayerDirections = Value
  val TOP, BOTTOM, RIGHT, LEFT, TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT = Value
}

object Player extends DefaultEntityStatistics{
  override val DAMAGE_DEFAULT: Int = 200
  override val SPEED_DEFAULT: Int = 5
  override val SIZE_DEFAULT: Int = 40
  val HEIGHT_FACTOR: Double = 1.5
  override val FIRE_RATE_DEFAULT: Double = 1.5
  private val MAX_HP = 10
  override val DEFAULT_HP: Int = Player.MAX_HP
  val SPRITE_VARIATIONS: Int = 10
  private val IMMUNITY_LENGTH: Int = 30
  private var playerSprite: Spritesheet = null
  private var hudSprite: Spritesheet = null

  private def initPlayerSprite(s: Spritesheet): Unit = playerSprite = s
  private def initHudSprite(s: Spritesheet): Unit = hudSprite = s

  SpritesManager.addSprites(SpritesheetModel("data/sprites/elijah.png", 28, 43), initPlayerSprite)
  SpritesManager.addSprites(SpritesheetModel("data/sprites/elijah_hud_hearts.png", 140, 26), initHudSprite)
}

class Player(pos: Position,  onPlayerKilled: () => Unit) extends Entity(pos, Player.SIZE_DEFAULT, (Player.SIZE_DEFAULT * Player.HEIGHT_FACTOR).toInt) {
  override var _hp = Player.DEFAULT_HP
  var damage: Int = Player.DAMAGE_DEFAULT
  private var _speed: Int = Player.SPEED_DEFAULT
  var size: Int = Player.SIZE_DEFAULT
  var fireRate: Double = Player.FIRE_RATE_DEFAULT
  
  private var spriteMovementIndex: Int = 0
  private var intermediateSpriteMovementIndex: Int = 1
  private var spriteMovementDirectionIndex: Int = 0
  private var hideSprite: Boolean = false
  
  private var diagonalMovementLength = getDiagonalLength
  private var immunityFrames: Boolean = false
  private var onFireCooldown: Boolean = false
  
  private var currentMovingDirection: PlayerDirections = null

  override def hp_=(newVal: Int): Unit = {
    var value = newVal
    if(value > Player.MAX_HP) {
      value = Player.MAX_HP
    }
    super.hp_=(value)
  }

  override def selfInit: Boolean = false

  override def speed: Int = this._speed
  override def speed_=(newVal: Int): Unit = {
    this._speed = newVal
    this.diagonalMovementLength = getDiagonalLength
  }

  override def draw(g: GdxGraphics): Unit = {
    val updatedY: Int = g.getScreenHeight - _position.y - (size * Player.HEIGHT_FACTOR).toInt
    if(!hideSprite) {
      g.draw(Player.playerSprite.sprites(spriteMovementDirectionIndex)(spriteMovementIndex), _position.x, updatedY, size, (size*Player.HEIGHT_FACTOR).toInt)
      val spriteIndex: Int = if (_hp - 1 >= 0) _hp - 1 else 3
      g.draw(Player.hudSprite.sprites(spriteIndex)(0), 30, 30, 140, 26)
    }
    // hitbox
    super.draw(g)
  }

  override def collision(obj: CollisionList): Unit = {
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
    Timer.in(Math.round(60.0 / fireRate).toInt, () => {onFireCooldown = false})
    val proj = new PlayerProjectile(this, direction)
    proj.damage = damage
  }
  
  private def handleSpriteVariation(): Unit = {
    if (intermediateSpriteMovementIndex % 4 == 0) {
      if ((spriteMovementIndex + 1) < SPRITE_VARIATIONS) spriteMovementIndex += 1 else spriteMovementIndex = 0
    }
    intermediateSpriteMovementIndex += 1
  }

  /**
   * This methods compute the length of a component of the diagonal for the player movement
   * @return The length of a component of the diagonal
   */
  private def getDiagonalLength: Int = Math.round(Math.sqrt(Math.pow(speed.toDouble, 2) / 2.0)).toInt

  override def damageEntity(amount: Int): Unit = {
    if(immunityFrames) return
    if(amount == 0) return
    this.hp = hp - amount
    if(amount > 0) {
      immunityFrames = true
      val timerStopper = Timer.every(6, () => {
        hideSprite = !hideSprite
      })
      Timer.in(Player.IMMUNITY_LENGTH, () => {
        immunityFrames = false
        hideSprite = false
        timerStopper()
      })
    }
  }

  private def move(oldPos: Position) : Position = {
    val newPos = oldPos.clonePos()
    var moved = true
    currentMovingDirection match {
      case PlayerDirections.TOP => {
        newPos.y -= speed
        spriteMovementDirectionIndex = 2
      }
      case PlayerDirections.BOTTOM => {
        newPos.y += speed
        spriteMovementDirectionIndex = 0
      }
      case PlayerDirections.LEFT => {
        newPos.x -= speed
        spriteMovementDirectionIndex = 3
      }
      case PlayerDirections.RIGHT => {
        newPos.x += speed
        spriteMovementDirectionIndex = 1
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

  override def doGameplayTick(): Unit = {


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

  override protected def _dispose(): Unit = {
    super._dispose()
    // we must first dispose the stage else room
    // where we died won't get disposed
    GameplayManager.stage.dispose()
    GameplayManager.dispose()
    GameplayManager.init()
  }

  override def getCollisionGroup(): CollisionGroupNames = CollisionGroupNames.Player
}
