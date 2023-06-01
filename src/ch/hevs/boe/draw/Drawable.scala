package ch.hevs.boe.draw

import ch.hevs.gdx2d.lib.GdxGraphics

trait Drawable {
  def draw(g: GdxGraphics): Unit
}
