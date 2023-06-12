package ch.hevs.boe

import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.draw.sprites.SpritesManager
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.Position
import ch.hevs.boe.stage.{ProceduralGeneration, Stage}
import ch.hevs.boe.utils.Initiable
import ch.hevs.boe.utils.time.Timeout
import ch.hevs.gdx2d.lib.GdxGraphics

object GameplayManager extends Initiable {
  private var _player: Player = null
  private var _stage: Stage = null
  private var depth: Int = 0

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

  def gameTick(g: GdxGraphics): Unit = {
    if(initiated) {
      DrawManager.onDraw(g)
    }
  }

  override protected def _init(): Unit = {
    SpritesManager.init()

    _player = new Player(new Position(250, 250))
    stage = ProceduralGeneration.generateStage()
    player.init()
    DrawManager.init()
  }

  override protected def _dispose(): Unit = {
    player = null
    stage = null
    DrawManager.dispose()
  }

  def goToNextStage(): Unit = {
    GameplayManager.stage = ProceduralGeneration.generateStage(GameplayManager.stage.depth + 1)
  }
}
