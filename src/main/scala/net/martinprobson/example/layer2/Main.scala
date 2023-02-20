package net.martinprobson.example.layer2

import zio.*
import net.martinprobson.example.ZIOApplication

case class Hello(hello: String)

object Hello:
  def sayHello: ZIO[Hello, Throwable, String] =
    ZIO.serviceWith[Hello](_.hello)

case class Hello2(hello: String)

object Hello2:
  def sayHello: ZIO[Hello2, Throwable, String] =
    ZIO.serviceWith[Hello2](_.hello)

object Main extends ZIOApplication:

  def openScope(name: String): ZIO[Scope, Throwable, Hello2] =
    ZIO.acquireRelease(open(name))(close)

  def open(name: String): Task[Hello2] =
    ZIO.logInfo(s"Getting a $name") *> ZIO.attempt(Hello2(name))

  def close(hello2: Hello2): UIO[Unit] =
    ZIO.logInfo(s"closing $hello2")

  val helloLayer: ULayer[Hello] = ZLayer.succeed(Hello("Hello world!"))

  val scopedLayer = ZLayer.scoped { openScope("Martin") }

//  def program: ZIO[Hello, Throwable, Unit] =
//    ZIO.environment[Hello].flatMap { env =>
//      val hello = env.get[Hello]
//      ZIO.logInfo(hello.hello)
//    }
  def program: ZIO[Hello & Hello2, Throwable, Unit] = for
    hello <- Hello.sayHello
    _ <- ZIO.logInfo(hello)
    hello2 <- Hello2.sayHello
    _ <- ZIO.logInfo(hello2)
  yield ()

  def program2: ZIO[Hello, Throwable, Unit] =
    ZIO.serviceWithZIO[Hello](h => ZIO.logInfo(s"Hello from program2 ${h.hello}"))

  override def run = program.provide(helloLayer ++ scopedLayer) *> program2.provide(helloLayer)
//  override def run = program2.provide(helloLayer)

end Main
