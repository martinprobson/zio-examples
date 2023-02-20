package net.martinprobson.example

import zio._

case class LoggerImpl() extends Logger {
  override def logPrint(msg: String): ZIO[Any,Throwable,Unit] = ZIO.succeed(println(msg))
}

object LoggerImpl {
  val layer: ZLayer[Any, Nothing, Logger] =
    ZLayer.succeed(LoggerImpl())
}
