package net.martinprobson.example.clock

import zio.*
import net.martinprobson.example.ZIOApplication

object ClockExample extends ZIOApplication:

  def printTime: Task[Unit] = Clock.currentDateTime.flatMap { t => ZIO.logInfo(s"$t") } *>
    ZIO.sleep(1.seconds) *> printTime

  def run = for {
    f1 <- printTime.fork
    f2 <- printTime.fork
    f3 <- printTime.fork
    f4 <- printTime.fork
    _ <- ZIO.sleep(10.seconds)
    _ <- f1.interrupt
    _ <- f2.interrupt
    _ <- f3.interrupt
    _ <- f4.interrupt
  } yield ()
