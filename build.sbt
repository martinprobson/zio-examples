import Dependencies._

ThisBuild / scalaVersion     := "3.3.4"
ThisBuild / version          := "0.0.1-SNAPSHOT"
ThisBuild / organization     := "net.martinprobson"

lazy val root = (project in file("."))
  .settings(
    name := "zio",
    libraryDependencies ++= zio,
    libraryDependencies ++= db,
    libraryDependencies ++= loggingLibs,
    libraryDependencies ++= configLibs,
    libraryDependencies ++= testLibs
  )

scalacOptions ++= Seq(
  "-deprecation",     // Emit warning and location for usages of deprecated APIs.
  "-explaintypes",    // Explain type errors in more detail.
  "-Xfatal-warnings" // Fail the compilation if there are any warnings.
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*)       => MergeStrategy.discard
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case _                                   => MergeStrategy.first
}
