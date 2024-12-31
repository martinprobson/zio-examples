package net.martinprobson.example.scope

import net.martinprobson.example.ZIOApplication
import zio.*

import java.io.{BufferedReader, File, FileInputStream, IOException, InputStreamReader}
import java.util.stream.Collectors

object ScopeTest extends ZIOApplication:
  private def acquire(name: => String): IO[IOException, FileInputStream] =
    def open(name: => String): FileInputStream = {
      val file = new File(name)
      new FileInputStream(file)
    }
    ZIO.logInfo(s"acquire $name") *> ZIO.attemptBlockingIO(open(name))

  private def release(fis: => FileInputStream): UIO[Unit] =
    ZIO.logInfo("release") *> ZIO.succeedBlocking(fis.close())

  private def source(name: => String): ZIO[Scope, IOException, FileInputStream] =
    ZIO.acquireRelease(acquire(name))(release(_))

  private def contents(name: => String): IO[IOException, String] =
    def read(fileInputStream: FileInputStream): String = {
      new BufferedReader(new InputStreamReader(fileInputStream))
        .lines().collect(Collectors.joining("\n"))
    }
    ZIO.scoped {
      source(name).flatMap { fileInputStream =>
        ZIO.attemptBlockingIO(read(fileInputStream))
      }
    }
//
  val run: ZIO[ZIOAppArgs & Scope, IOException, Unit] = for {
    _ <- ZIO.logInfo("Start")
    contents <- contents("/home/martinr/Downloads/gcp-cloud-function-test-cf6f8304bb7f.json")
    _ <- ZIO.logInfo(contents)
    _ <- ZIO.logInfo("Done")
  } yield ()

end ScopeTest
