package ch.hevs.boe.utils

trait Initiable {
  private var _initiated: Boolean = false

  def initiated: Boolean = _initiated
  def initiated_=(newVal: Boolean): Unit = {
    _initiated = newVal
  }
  final def init(): Unit = {
    if(initiated) return
    initiated = true
    this._init()
  }

  final def dispose(): Unit = {
    if(!initiated) return
    initiated = false
    this._dispose()
  }

  protected def _init(): Unit

  protected def _dispose(): Unit
}
