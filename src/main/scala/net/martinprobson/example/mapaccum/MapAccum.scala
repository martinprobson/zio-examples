package net.martinprobson.example.mapaccum

import zio._
import zio.stream._
import net.martinprobson.example.ZIOApplication

object MapAccum extends ZIOApplication {
  val stream: ZStream[Any, Nothing, Int] = ZStream(1, 2, 3, 4, 5)

  case class Mapped(element: Int, accum: String)

  val initialState = 0

  val mappedStream: ZStream[Any, Nothing, Mapped] =
    stream.mapAccum(initialState) { (state, element) =>
      val newState = state + element
      val mappedElement = (element, s"Accumulated: $newState ")
      (newState, Mapped(element, s"New state: $newState"))
    }

  val program: ZIO[Any, Nothing, Chunk[Mapped]] = mappedStream.runCollect

  def run = for {
    res <- program
    _ <- ZIO.logInfo(s"Res = $res")
    _ <- ZIO.logInfo("Done")
  } yield ()
}
