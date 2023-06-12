package ch.hevs.boe.draw

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.utils.Initiable
import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, HashMap}

case class DrawManagerObject(cb: DrawManagerCallback, zIndex: Int)

object DrawManager extends Initiable {
  private val subscribers: HashMap[Int, DrawManagerObject] = new HashMap[Int, DrawManagerObject]()
  private val orderedIndexes: ArrayBuffer[Int] = new ArrayBuffer[Int]()
  private var currentIndex = 0
  def subscribe(cb: DrawManagerCallback, zIndex:Int): Int = {
    this.subscribers.addOne(currentIndex, DrawManagerObject(cb, zIndex))
    insertIndex(currentIndex, zIndex)
    val old = currentIndex
    currentIndex += 1
    return old
  }

  private def insertIndex(elementId: Int, zi: Int): Unit = {
    val clone = orderedIndexes.clone
    for(i <- 0 until clone.length) {
      println("Checking " + subscribers(clone(i)).zIndex + " with " + zi)
      if(subscribers(clone(i)).zIndex < zi) {
        // This means that we have to insert it to the previous place
        orderedIndexes.insert(i, elementId)
        println("inserted index, new val ", orderedIndexes)
        return
      }
    }

    orderedIndexes.addOne(elementId)
    println("inserted index last new val : ", orderedIndexes)
  }

  def unsubscribe(i: Int): Unit = {
    if(subscribers.contains(i)) {
      subscribers.remove(i)
      val clone = orderedIndexes.clone
      for(j <- 0 until clone.length) {
        if(clone(j) == i) {
          orderedIndexes.remove(j)
        }
      }
    } else {
      println("Subscriber not found !")
    }
  }

  def onDraw(g: GdxGraphics): Unit = {
    if(!initiated) return
    g.clear()
    val clone = orderedIndexes.clone
    val sclone = subscribers.clone
    for(i <- clone) {
      sclone(i).cb(g)
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
