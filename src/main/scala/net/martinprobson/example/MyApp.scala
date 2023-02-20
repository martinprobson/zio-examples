package net.martinprobson.example

import zio.*
import java.io.File

object MyApp extends ZIOApplication:

  def createTempFile(prefix: String, extension: String): Task[File] =
    ZIO.logInfo(s"Creating temp file with prefix: $prefix and extension: $extension") *>
      ZIO.attemptBlocking(File.createTempFile(prefix, extension))

  def deleteTempFile(file: File): UIO[Unit] =
    ZIO.logInfo(s"Deleting: ${file.getPath}") *>
      ZIO
        .attemptBlocking(file.delete())
        .flatMap(_ => ZIO.logInfo(s"Deleted: ${file.getPath}"))
        .orDie

  def tempFile(prefix: String, extension: String): ZIO[Scope, Throwable, File] =
    ZIO.acquireRelease(createTempFile(prefix, extension))(deleteTempFile)

  def openScope(name: String): ZIO[Scope, Throwable, String] =
    ZIO.acquireRelease(open(name))(close)

  def open(name: String): Task[String] =
    ZIO.logInfo(s"Getting a $name") *> ZIO.attempt(name)

  def close(name: String): UIO[Unit] =
    ZIO.logInfo(s"closing $name")

  def program = for
    _ <- ZIO.logInfo("Hello world!")
    _ <- ZIO.scoped {
      for {
        t1 <- tempFile("temp", ".txt")
        _ <- ZIO.logInfo(s"Using ${t1.getPath}")
        t2 <- tempFile("temp", ".txt")
        _ <- ZIO.logInfo(s"Using ${t2.getPath}")
        t3 <- tempFile("temp", ".txt")
        _ <- ZIO.logInfo(s"Using ${t3.getPath}")
        t4 <- tempFile("temp", ".txt")
        _ <- ZIO.logInfo(s"Using ${t3.getPath}")
      } yield ()
    }
    _ <- ZIO.scoped {
      openScope("scoped2").flatMap(name => ZIO.logInfo(s"Using $name"))
    }
    name <- openScope("scoped")
    _ <- ZIO.logInfo(s"Using $name")
    r <- ZIO.acquireRelease(open("fred"))(close)
    _ <- ZIO.logInfo(s"Using resource $r")
  yield ()

  override def run = ZIO.scoped { program }

end MyApp
