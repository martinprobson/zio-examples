package net.martinprobson.example.foreachpar

import net.martinprobson.example.ZIOApplication
import zio.*
import User.*

object Main extends ZIOApplication:
  //val run = {
  //  val r = Range(1, 20000).toList
  //    .map { i => User(UserName(s"User-$i"), Email(s"email-$i")) }
  //  ZIO.foreachPar(r)(u => ZIO.blocking(ZIO.logInfo(s"User: $u")))
  //}

  val run = {
    val r = Range(1, 20000).toList
      .map { i => User(UserName(s"User-$i"), Email(s"email-$i")) }
    ZIO.foreachPar(r)(u => ZIO.blocking(ZIO.logInfo(s"User: $u")))
  }

end Main
