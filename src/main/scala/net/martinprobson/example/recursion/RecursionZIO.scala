package net.martinprobson.example.recursion

import net.martinprobson.example.ZIOApplication
import zio.*

object RecursionZIO extends ZIOApplication {

  def factorial(n: Int): Task[BigInt] =
    if (n == 0)
      ZIO.succeed(1)
    else
      ZIO.suspend(factorial(n - 1)).flatMap(r => ZIO.attempt(r * n))

  def run = for {
    result <- factorial(1000000)
    _ <- ZIO.logInfo(s"Result is $result")
  } yield ()

}
