package net.martinprobson.example

import zio._

trait Logger {
  def logPrint(msg: String): ZIO[Any, Throwable, Unit]

}

object Logger {
  def logPrint(msg: String) =
    ZIO.serviceWithZIO[Logger](_.logPrint(msg))
}
