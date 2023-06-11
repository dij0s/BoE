package ch.hevs.boe.utils.time

protected class Timer(t: javax.swing.Timer) {
  def start = t.start
  def stop = t.stop
}

object Timer {
  def apply(ms: Int, repeat: Boolean = true)(op: => Unit): Timer = {
    val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e: java.awt.event.ActionEvent) = op
    }
    val t = new javax.swing.Timer(ms, timeOut)
    t.setRepeats(repeat)
    t.start()
    return new Timer(t)
  }
}

object Timeout {
  def apply(ms: Int)(op: => Unit) = {
    Timer(ms, false) {op}
  }
}
