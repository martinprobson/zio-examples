package net.martinprobson.example.layer3

import zio.*
import net.martinprobson.example.ZIOApplication

import zio._

case class DatabaseConfig()

object DatabaseConfig:
  val live =
    ZLayer.fromZIO(ZIO.logInfo("Making DatabaseConfig layer")) ++ ZLayer.succeed(DatabaseConfig())

case class Database(databaseConfig: DatabaseConfig)

object Database:
  val live: ZLayer[DatabaseConfig, Nothing, Database] =
    ZLayer.fromZIO(ZIO.logInfo("Making Database layer")) ++ ZLayer.fromFunction(Database.apply _)

case class Analytics()

object Analytics:
  val live: ULayer[Analytics] =
    ZLayer.fromZIO(ZIO.logInfo("Making Analytics layer")) ++ ZLayer.succeed(Analytics())

case class Users(database: Database, analytics: Analytics)

object Users:
  val live = ZLayer.fromZIO(ZIO.logInfo("Making Users layer")) ++ ZLayer.fromFunction(Users.apply _)

case class App(users: Users, analytics: Analytics):
  def execute: UIO[Unit] =
    ZIO.debug(s"This app is made from ${users} and ${analytics}")

object App:
  val live = ZLayer.fromZIO(ZIO.logInfo("Making App layer")) ++ ZLayer.fromFunction(App.apply _)

object Main extends ZIOApplication:

//  def run =
//    ZIO
//      .serviceWithZIO[App](_.execute)
//      .provide(
//        (((DatabaseConfig.live >>> Database.live) ++ Analytics.live >>> Users.live) ++ Analytics.live) >>> App.live
//      )
  def run =
    ZIO
      .serviceWithZIO[App](_.execute)
      .provide(
        DatabaseConfig.live,Database.live,Analytics.live,App.live,Users.live
      )
