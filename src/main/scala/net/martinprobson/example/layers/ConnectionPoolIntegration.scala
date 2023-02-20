package net.martinprobson.example.layers

import zio.*
import zio.managed.*

// Integration with ZIO
object ConnectionPoolIntegration {
  def createConnectionPool(dbConfig: DBConfig): ZIO[Any, Throwable, ConnectionPool] =
    ZIO.attempt(new ConnectionPool(dbConfig.url))

  val closeConnectionPool: ConnectionPool => ZIO[Any, Nothing, Unit] =
    (cp: ConnectionPool) => ZIO.attempt(cp.close()).catchAll(_ => ZIO.unit)

  def managedConnectionPool(dbConfig: DBConfig): ZManaged[Any, Throwable, ConnectionPool] =
    ZManaged.acquireReleaseWith(createConnectionPool(dbConfig))(closeConnectionPool)

  val live: ZLayer[DBConfig, Throwable, ConnectionPool] =
    ZManaged.service[DBConfig].flatMap(dbConfig => managedConnectionPool(dbConfig)).toLayer

}
