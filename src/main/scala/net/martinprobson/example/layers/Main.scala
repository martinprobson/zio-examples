package net.martinprobson.example.layers

import zio.*
import net.martinprobson.example.ZIOApplication

object Main extends ZIOAppDefault {
//  override type Environment = DB

//  override val tag: Tag[Environment] = Tag[Environment]

  val layer: ZLayer[Any, Any, DB] =
    ZLayer.make[DB](
      ConnectionPoolIntegration.live,
      DB.live,
      ZLayer.succeed(DBConfig("jdbc://localhost"))
    )

  override val run =
    (UserRegistration
      .register(User("Martin", "martinprobson@gmail.com"))
      .map { u => println(s"Registered user: $u (layers)") })
      .provide(layer)
}
