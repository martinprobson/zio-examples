package net.martinprobson.example.async

import net.martinprobson.example.ZIOApplication
import zio.*

object Fork extends ZIOApplication {

  def doWork(n: Int): UIO[Int] =
    ZIO.logInfo(s"doWork $n") *> ZIO.succeed(1)

  override def run = for {
    res <- ZIO
      .foreach(1 to 10000)(n => doWork(n).fork)
      .flatMap(f => Fiber.collectAll(f).join)
    s <- ZIO.succeed(res.fold(0)(_ + _))
    _ <- ZIO.logInfo(s"s = $s")
  } yield ()

}
