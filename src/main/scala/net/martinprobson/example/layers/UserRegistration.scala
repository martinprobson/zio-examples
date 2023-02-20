package net.martinprobson.example.layers

import zio.*

object UserRegistration:
  def register(u: User): ZIO[DB, Throwable, User] =
    for {
      _ <- UserModel.insert(u)
      _ <- UserNotifier.notify(u, "Welcome!")
    } yield u
