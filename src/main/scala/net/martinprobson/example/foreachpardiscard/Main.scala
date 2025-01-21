package net.martinprobson.example.foreachpardiscard

import net.martinprobson.example.ZIOApplication
import zio.*
import User.*

object Main extends ZIOApplication:

  val prepareCoffee: Task[Unit] = {
    ZIO.logInfo("Preparing coffee") *>
      ZIO.sleep(5.seconds) *>
      ZIO.logInfo("Coffee made!")
  }

  val boilKettle: Task[Unit] = {
    ZIO.logInfo("Boiling Kettle") *>
      ZIO.sleep(1.seconds) *>
      ZIO.logInfo("Kettle boiled")
  }

  val bathTime: Task[Unit] = {
    ZIO.logInfo("Preparing bath") *>
      ZIO.sleep(10.seconds) *>
      ZIO.logInfo("bath finished!")
  }

  val run: Task[Unit] = for {
    _ <- ZIO.logInfo("Start")
    _ <- ZIO.collectAllParDiscard(Seq(bathTime, boilKettle))
    _ <- prepareCoffee
    _ <- ZIO.logInfo("Done")
  } yield ()

end Main
