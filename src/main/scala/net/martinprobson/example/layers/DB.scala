package net.martinprobson.example.layers

import zio.*

// service
trait DB:
  def execute(sql: String): Task[Unit]

object DB:
  // Implementation
  case class RelationalDB(cp: ConnectionPool) extends DB:
    override def execute(sql: String): Task[Unit] =
      ZIO.attempt(println(s"Running $sql on $cp"))

  // layer
  val live: ZLayer[ConnectionPool, Throwable, DB] = ZLayer {
    for con <- ZIO.service[ConnectionPool]
    yield RelationalDB(con)
  }

  // Accessor
  def execute(sql: String): ZIO[DB, Throwable, Unit] =
    ZIO.serviceWithZIO(_.execute(sql))
