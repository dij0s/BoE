package ch.hevs.boe

import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import ch.hevs.gdx2d.components.bitmaps.Spritesheet

class HelloWorldApplication extends PortableApplication(1200,1000) {
  private var isaacSprites: Spritesheet = _
  override def onInit(): Unit = {
    setTitle("Hello world application")
    isaacSprites = new Spritesheet("data/sprites/isaac_og_sheet.png", 64, 64)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.clear()
    g.drawFPS()
    g.draw(isaacSprites.sprites(0)(0), 0, 0)
  }
}

object Runner extends App {
  new HelloWorldApplication
}
