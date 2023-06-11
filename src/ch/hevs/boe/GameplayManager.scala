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


  def stage = _stage
  private def stage_=(newVal: Stage) = {
    _stage = newVal
    if(_stage != null) {
      _stage.init()
    } else {
      println("[GM] Stage is set to null")
    }
  }

  def player = _player
  private def player_=(newVal: Player) = {
    _player = newVal
    if(_player != null) {
      _player.init()
    } else {
      println("[GM] Player is set to null")
    }
  }


  def gameTick(g: GdxGraphics) = {
    if(initiated) {
      DrawManager.onDraw(g)
    }
  }

  override protected def _init(): Unit = {
    SpritesManager.init()
    _player = new Player(new Position(250, 250))
    stage = ProceduralGeneration.generateStage(player)
    player.init()
    DrawManager.init()
  }

  override protected def _dispose(): Unit = {
    player = null
    stage = null
    DrawManager.dispose()
    SpritesManager.dispose()
  }
}
