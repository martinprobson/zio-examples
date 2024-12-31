import sbt._

object Dependencies {
  lazy val testLibs = Seq(
    "org.scalactic" %% "scalactic" % "3.2.19" % Test,
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )

  lazy val configLibs = Seq("com.typesafe" % "config" % "1.4.3")

  lazy val loggingLibs = Seq(
    "org.slf4j" % "slf4j-api" % "2.0.0-alpha4",
    "ch.qos.logback" % "logback-classic" % "1.5.12",
    "ch.qos.logback" % "logback-core" % "1.5.12",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
  )

  lazy val zio = Seq(
    "dev.zio" %% "zio-interop-cats" % "23.1.0.3",
    "dev.zio" %% "zio" % "2.1.13",
    "dev.zio" %% "zio-logging" % "2.4.0",
    "dev.zio" %% "zio-logging-slf4j" % "2.4.0",
    "dev.zio" %% "zio-managed" % "2.1.13",
    "dev.zio" %% "zio-streams" % "2.1.13"
  )

  lazy val db = Seq(
    "org.tpolecat" %% "doobie-core" % "1.0.0-RC6",
    "org.tpolecat" %% "doobie-hikari" % "1.0.0-RC6",
    "mysql" % "mysql-connector-java" % "8.0.33",
    "com.h2database" % "h2" % "1.4.200"
  )
}
