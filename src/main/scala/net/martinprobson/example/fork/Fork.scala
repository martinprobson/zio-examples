package net.martinprobson.example.async

import net.martinprobson.example.ZIOApplication
import zio.*

object Fork extends ZIOApplication:

  def doWork(n: Int): ZIO[Any, Nothing, Int] =
    ZIO.logInfo(s"doWork $n") *> ZIO.succeed(n)

  override def run =
    ZIO.foreach(1 to 100000)(n => doWork(n).fork).flatMap(f => Fiber.collectAll(f).join)

end Fork
