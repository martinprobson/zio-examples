package net.martinprobson.example.catsinterop

import doobie.*
import doobie.implicits.*
import doobie.hikari.HikariTransactor
import fs2.Stream
import zio.*
import zio.interop.catz.*
import net.martinprobson.example.ZIOApplication

object Main extends ZIOApplication {

  case class User(id: String, name: String, age: Int)

  private def xa: Transactor[Task] =
    Transactor.fromDriverManager[Task](
      "com.mysql.cj.jdbc.Driver",
      "jdbc:mysql://localhost:3306/test2",
      "root",
      "root", None: Option[LogHandler[Task]]
    )


  private def createTable: ConnectionIO[Int] =
    sql"""
         |create table if not exists users
         |(
         |    id   int auto_increment
         |        primary key,
         |    name varchar(100) null,
         |    age int
         |);
         |""".stripMargin.update.run

  private def dropTable: doobie.ConnectionIO[Int] =
    sql"""DROP TABLE IF EXISTS USERS""".update.run

  def insert(name: String, age: Int): doobie.ConnectionIO[Int] =
    sql"insert into users (name, age) values ($name, $age)".update.run

  private def loadUsers(xa: Transactor[Task]): Task[List[User]] =
    sql"""SELECT * FROM users""".query[User].stream.transact(xa).compile.toList

  private def insertUsers(xa: Transactor[Task]): Task[Unit] =
    val l = Range
    .inclusive(1, 1000)
    .toList
    .map(i => ZIO.logInfo(s"Insert $i") *> insert(s"User-$i",i).transact(xa))
    ZIO.forkAll(l) *> ZIO.logInfo("Done!")

  private def doobieApp(xa: Transactor[Task]): Task[List[User]] = for {
    _ <- ZIO.logInfo("In doobieApp2")
    _ <- dropTable.transact(xa)
    _ <- createTable.transact(xa)
    _ <- insertUsers(xa)
    _ <- insert("Fred",21).transact(xa)
    _ <- insert("Fred2",22).transact(xa)
    u <- loadUsers(xa)
  } yield u

  val program: Task[List[User]] = doobieApp(xa)

  override val run: Task[Unit] = for {
    _ <- ZIO.logInfo("Hello!")
    users <- program
    _ <- ZIO.logInfo(s"Got $users")
  } yield ()

}
