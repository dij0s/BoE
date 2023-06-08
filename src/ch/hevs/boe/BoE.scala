package ch.hevs.boe

import ch.hevs.boe.draw.sprites.SpritesManager
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.stage.{ProceduralGeneration, Stage}
import ch.hevs.boe.stage.room.predefined.SpawnRoom
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Game extends PortableApplication(900, 600) {

  // TODO: implement camera based on user position (inside Player class ig ?)

  private val player: Player = new Player(new Position(250, 250))
  private var startStage: Stage = null

  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")
    startStage = ProceduralGeneration.generateStage()
    SpritesManager.init()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    player.doGameplayTick
    // must make sure to draw stage before any entity
    startStage.draw(g)
//    startStage.compileGraph()
    player.draw(g)
    CollisionManager.checkCollisions()
  }
}

object BoERunner extends App {
  new Game
}

