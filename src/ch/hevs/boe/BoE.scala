package ch.hevs.boe

import ch.hevs.boe.draw.DrawManager
import ch.hevs.boe.draw.sprites.SpritesManager
import ch.hevs.boe.entity.Entity
import ch.hevs.boe.entity.mob.{Firemen, Mob}
import ch.hevs.boe.entity.mob.boss.Tank
import ch.hevs.boe.entity.player.Player
import ch.hevs.boe.physics.{CollisionManager, Position}
import ch.hevs.boe.stage.{ProceduralGeneration, Stage}
import ch.hevs.boe.stage.room.predefined.SpawnRoom
import ch.hevs.boe.utils.time.Timer
import ch.hevs.gdx2d.components.bitmaps.Spritesheet
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics

class Game extends PortableApplication(900, 600) {

  // TODO: implement camera based on user position (inside Player class ig ?)
  override def onInit(): Unit = {
    setTitle("The Binding of Elijah")
    GameplayManager.init()
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    GameplayManager.gameTick(g)
  }
}

object BoERunner extends App {
  new Game
}

