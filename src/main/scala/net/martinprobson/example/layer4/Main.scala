package net.martinprobson.example.layer4

import zio.*
import net.martinprobson.example.ZIOApplication

case class Person(private val name: String):
  def getName: ZIO[Any, Nothing, String] = ZIO.succeed(name)

object Person:
  def apply: ZIO[Any, Nothing, Person] = ZIO.succeed(new Person("Martin"))

object Main extends ZIOApplication:

  def program: ZIO[Person, Throwable, Unit] = for
    name <- ZIO.serviceWithZIO[Person](_.getName)
    _ <- ZIO.logInfo(s"Hello $name !")
    _ <- ZIO.logInfo(s"This is a message")
  yield ()

//  override def run: ZIO[Any, Throwable, Unit] = program.provide(ZLayer(Person.apply))
  override def run: ZIO[Any, Throwable, Unit] =
    program.provide(ZLayer.fromFunction(() => Person("FRED")))

end Main
