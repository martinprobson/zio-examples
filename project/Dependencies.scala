import sbt._

object Dependencies {
  lazy val testLibs = Seq(
    "org.scalactic" %% "scalactic" % "3.2.14" % Test,
    "org.scalatest" %% "scalatest" % "3.2.14" % Test
  )

  lazy val configLibs = Seq("com.typesafe" % "config" % "1.4.2")

  lazy val loggingLibs = Seq(
    "org.slf4j" % "slf4j-api" % "2.0.0-alpha4",
    "ch.qos.logback" % "logback-classic" % "1.3.0-alpha10",
    "ch.qos.logback" % "logback-core" % "1.3.0-alpha10",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
  )

  lazy val zio = Seq(
    "dev.zio" %% "zio-interop-cats" % "3.3.0",
    "dev.zio" %% "zio" % "2.0.16",
    "dev.zio" %% "zio-logging" % "2.1.5",
    "dev.zio" %% "zio-logging-slf4j" % "2.1.5",
    "dev.zio" %% "zio-managed" % "2.0.16",
    "dev.zio" %% "zio-streams" % "2.0.16"
  )

  lazy val db = Seq(
    "org.tpolecat" %% "doobie-core" % "1.0.0-RC1",
    "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC1",
    "mysql" % "mysql-connector-java" % "8.0.30",
    "com.h2database" % "h2" % "1.4.200"
  )
}
