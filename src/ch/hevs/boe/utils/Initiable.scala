package ch.hevs.boe.utils

trait Initiable {
  private var _initied: Boolean = false

  def initied: Boolean = _initied
  def initied_=(newVal: Boolean) = {
    _initied = newVal
  }
  final def init(): Unit = {
    if(initied) return
    initied = true
    this._init()
  }

  final def dispose(): Unit = {
    if(!initied) return
    initied = false
    this._dispose()
  }

  protected def _init(): Unit

  protected def _dispose(): Unit
}
