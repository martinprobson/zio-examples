package net.martinprobson.example.recursion

import net.martinprobson.example.ZIOApplication
import zio.*
import doobie.hi.resultset

object RecursionZIO extends ZIOApplication {

  def factorial(n: BigInt): Task[BigInt] =
    if (n == 0) {
      ZIO.succeed(1)
    } else {
      for {
        res <- factorial(n - 1)
        result <- ZIO.attempt(n * res)
      } yield result
    }

  def run = for {
    result <- factorial(BigInt(100000))
    _ <- ZIO.logInfo(s"Result is $result")
  } yield ()

}
