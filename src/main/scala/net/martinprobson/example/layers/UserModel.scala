package net.martinprobson.example.layers

import zio.*

object UserModel:
  def insert(u: User): ZIO[DB, Throwable, Unit] =
    DB.execute(s"INSERT INTO user VALUES ('${u.name}')")
