package ch.hevs.boe.draw

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.utils.Initiable
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.HashMap

object DrawManager extends Initiable {
  private val subscribers: HashMap[Int, DrawManagerCallback] = new HashMap[Int, DrawManagerCallback]()
  private var currentIndex = 0
  def subscribe(cb: DrawManagerCallback): Int = {
    this.subscribers.addOne(currentIndex, cb)
    var old = currentIndex
    currentIndex += 1
    return old
  }

  def unsubscribe(i: Int): Unit = {
    if(subscribers.contains(i)) {
      subscribers.remove(i)
    } else {
      println("Subscriber not found !")
    }
  }

  def onDraw(g: GdxGraphics): Unit = {
    if(!initiated) return
    g.clear()
    val clone = subscribers.clone()
    for(cb <- clone.values) {
      cb(g)
    }
    g.drawFPS()

  }

//  def onDraw(g: GdxGraphics): Unit = subscribers.values.foreach(_(g))

  override protected def _init(): Unit = {
    println("Init draw manager")
  }

  override protected def _dispose(): Unit = {
    println("Dispose draw manager")
  }
}
