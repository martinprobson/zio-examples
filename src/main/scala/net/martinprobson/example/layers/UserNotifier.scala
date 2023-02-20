package net.martinprobson.example.layers

import zio.*

object UserNotifier:
  def notify(u: User, msg: String): ZIO[DB, Throwable, Unit] =
//    Clock.currentDateTime.flatMap { dateTime =>
//      ZIO.attempt(s"Sending $msg to ${u.email} @ $dateTime")
    ZIO.attempt(s"Sending $msg to ${u.email}")
