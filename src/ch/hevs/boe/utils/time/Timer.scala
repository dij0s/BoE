package ch.hevs.boe.utils.time

import scala.collection.mutable.{ArrayBuffer, HashMap}

object Timer {
  private var currentFrames: Int = 0

  def frame: Int  = currentFrames

  private val toTrigger: HashMap[Int, ArrayBuffer[() => Unit]] = new HashMap[Int, ArrayBuffer[() => Unit]]()


  def tick() = {
    currentFrames += 1
    if(toTrigger.contains(currentFrames)) {
      for(c <- toTrigger(currentFrames)) {
        c()
      }
      toTrigger.remove(currentFrames)
    }
  }

  def in(frames: Int, cb: () => Unit) = {
    addCbTrigger(frames, cb)
  }

  private def addCbTrigger(frames: Int, cb: () => Unit) = {
    val target: Int = currentFrames + frames
    if (!toTrigger.contains(target)) {
      toTrigger.addOne(target, new ArrayBuffer[() => Unit]())
    }
    toTrigger(target).addOne(cb)
  }

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
