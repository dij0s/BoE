package ch.hevs.boe.utils.time

import scala.collection.mutable.{ArrayBuffer, HashMap}

object Timer {
  private var currentFrames: Int = 0

  /**
   * Get the current frame number from the start (Don't use this to count frames. use it for with modulo only)
   * @return - The current frame number from the start of the program
   */
  def frame: Int  = currentFrames

  private val toTrigger: HashMap[Int, ArrayBuffer[() => Unit]] = new HashMap[Int, ArrayBuffer[() => Unit]]()


  /**
   * To call every frame
   */
  def tick(): Unit = {
    currentFrames += 1
    if(toTrigger.contains(currentFrames)) {
      for(c <- toTrigger(currentFrames)) {
        c()
      }
      toTrigger.remove(currentFrames)
    }
  }

  /**
   * Trigger the <callback> in <frames> frames
   * @param frames - The number of frames to wait before triggering the callback
   * @param cb - The callback to trigger
   */
  def in(frames: Int, cb: () => Unit): Unit = {
    addCbTrigger(frames, cb)
  }

  private def addCbTrigger(frames: Int, cb: () => Unit) = {
    val target: Int = currentFrames + frames
    if (!toTrigger.contains(target)) {
      toTrigger.addOne(target, new ArrayBuffer[() => Unit]())
    }
    toTrigger(target).addOne(cb)
  }

  /**
   * Trigger the <callback> every <frames> frames
   * @param frames - The number of frames in between triggers
   * @param cb - The callback to trigger
   * @return - A method to dispose the timer
   */
  def every(frames: Int, cb: () => Unit): () => Unit = {
    var active = true
    def addTrigger: Unit = {
      addCbTrigger(frames, () => {
        if(active) {
          cb()
          addTrigger
        }
      })
    }
    addTrigger
    return () => {
      active = false
    }
  }
}
