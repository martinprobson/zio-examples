package net.martinprobson.example.async

import net.martinprobson.example.ZIOApplication
import zio.{Console, Task, UIO, ZIO, IO}

import java.io.IOException
import Legacy.*

object Async extends ZIOApplication:

  val login: IO[AuthError, User] =
    ZIO.async[Any,AuthError,User] { callback =>
      Legacy.login(
        user => callback(ZIO.succeed(user)),
        err => callback(ZIO.fail(err))
      )
    }
  override def run = for {
    _ <- ZIO.logInfo("Start")
    res <- login
    _ <- ZIO.logInfo(s"Result: $res")
  } yield ()

end Async

