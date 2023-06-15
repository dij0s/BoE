package ch.hevs.boe

import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.draw.sprites.SpritesManager
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.stage.{ProceduralGeneration, Stage}
import ch.hevs.boe.utils.Initiable
import ch.hevs.boe.utils.time.Timer
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter

object GameplayManager extends Initiable {
  val screenSize: (Int, Int) = (900, 600)
  
  private var _defaultFont: BitmapFont = null

  private var _player: Player = null
  private var _stage: Stage = null
  private var _depth: Int = 0
  
  def defaultFont: BitmapFont = _defaultFont

  def stage: Stage = _stage
  def stage_= (newVal: Stage): Unit = {
    if (_stage != null) _stage.dispose()
    _stage = newVal
    if(_stage != null) {
      _stage.init()
    } else {
      println("[GM] Stage is set to null")
    }
  }

  def player: Player = _player
  private def player_= (newVal: Player): Unit = {
    if (_player != null) _player.dispose()
    _player = newVal
    if(_player != null) {
      _player.init()
    } else {
      println("[GM] Player is set to null")
    }
  }

  def depth: Int = _depth
  
  def gameTick(g: GdxGraphics): Unit = {
    if(initiated) {
      DrawManager.onDraw(g)
    }
    CollisionManager.checkCollisions()
    Timer.tick()
  }
  
  def restartGame(): Unit = {
//    GameplayManager.stage.dispose()
    player = new Player(new Position(250, 250), () => {})
    player.init()
    stage = ProceduralGeneration.generateStage()
  }

  override protected def _init(): Unit = {
    val karmaticSource: FileHandle = Gdx.files.internal("data/fonts/karmatic-arcade.ttf")
    val fontGenerator: FreeTypeFontGenerator = new FreeTypeFontGenerator(karmaticSource)
    val fontParameters: FreeTypeFontParameter = new FreeTypeFontParameter()
    fontParameters.size = 32
    _defaultFont = fontGenerator.generateFont(fontParameters)
    fontGenerator.dispose()
    SpritesManager.init()
    _player = new Player(new Position(250, 250), () => {})
    stage = ProceduralGeneration.generateStage()
    _depth = 0
    player.init()
    DrawManager.init()
  }

  override protected def _dispose(): Unit = {
    player = null
    stage = null
    DrawManager.dispose()
  }

  def goToNextStage(): Unit = {
    _depth += 1
    GameplayManager.stage = ProceduralGeneration.generateStage(GameplayManager.depth)
  }
}
