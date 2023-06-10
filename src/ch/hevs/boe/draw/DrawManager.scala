package ch.hevs.boe.draw

import ch.hevs.boe.GenStuff._
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap

object DrawManager {
  private val subscribers: HashMap[Int, DrawManagerCallback] = new HashMap[Int, DrawManagerCallback]()
  private var currentIndex = 0
  def subscribe(cb: DrawManagerCallback): Int = {
    this.subscribers.addOne(currentIndex, cb)
    var old = currentIndex
    currentIndex += 1
    return old
  }

  def unsubscribe(i: Int) = {
    if(subscribers.contains(i)) {
      subscribers.remove(i)
    } else {
      println("Subscriber not found !")
    }
  }

  def onDraw(g: GdxGraphics): Unit = {
    val clone = subscribers.clone()
    for(cb <- clone.values) {
      cb(g)
    }
  }

//  def onDraw(g: GdxGraphics): Unit = subscribers.values.foreach(_(g))
}
