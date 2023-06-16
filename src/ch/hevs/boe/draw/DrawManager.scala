package ch.hevs.boe.draw

import ch.hevs.boe.GenStuff._
import ch.hevs.boe.utils.Initiable
import ch.hevs.gdx2d.lib.GdxGraphics
import scala.collection.mutable.{ArrayBuffer, HashMap}

case class DrawManagerObject(cb: DrawManagerCallback, zIndex: Int)

object DrawManager extends Initiable {
  // This hashmap will containt id:{callback, zindex}
  private val subscribers: HashMap[Int, DrawManagerObject] = new HashMap[Int, DrawManagerObject]()

  // This array will always be ordered by zIndex
  // It will contain the id of the hashmap
  private val orderedIndexes: ArrayBuffer[Int] = new ArrayBuffer[Int]()
  private var currentIndex = 0

  /**
   *
   * @param cb - The callback that will be executed on each draw
   * @param zIndex - The zIndex of your object -> higher is background -> see package.scala for implementation
   * @return - The id of the subscription -> to pass to unsubscribe method
   */
  def subscribe(cb: DrawManagerCallback, zIndex:Int): Int = {
    val old = currentIndex
    currentIndex += 1
    this.subscribers.addOne(old, DrawManagerObject(cb, zIndex))
    insertIndex(old, zIndex)
    old
  }

  private def insertIndex(elementId: Int, zi: Int): Unit = {
    val clone = orderedIndexes.clone
    for(i <- clone.indices) {
      // Looping through all already added cb method to find where to add the new one
      if(subscribers(clone(i)).zIndex < zi) {
        // This means that we have to insert it here
        orderedIndexes.insert(i, elementId)
        return
      }
    }
    orderedIndexes.addOne(elementId)
  }

  /**
   * Unsubscribe your method of the draw manager
   * @param i - The index returned by the subscribe method
   */
  def unsubscribe(i: Int): Unit = {
    if(subscribers.contains(i)) {
      subscribers.remove(i)
      var found = false
      val clone = orderedIndexes.clone
      for(j <- clone.indices) {
        if(clone(j) == i) {
          found = true
          orderedIndexes.remove(j)
        }
      }
      if(!found) {
        println("!!!! Key not found in ordered index")
      }
    } else {
      println("Subscriber not found !")
    }
  }

  /**
   * Trigger all callback of all subscribers
   * @param g
   */
  def onDraw(g: GdxGraphics): Unit = {
    if(!initiated) return
    g.clear()
    val clone = orderedIndexes.clone
    val sclone = subscribers.clone
    for(i <- clone) {
      if(sclone.contains(i)) {
        sclone(i).cb(g)
      }
    }
    g.drawFPS()
  }

  override protected def _init(): Unit = {

  }

  override protected def _dispose(): Unit = {

  }
}
