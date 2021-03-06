scalaVersion := "2.13.8"

version := "1.0"

val scalatestV = "3.2.10"
val catsV = "2.6.1"

lazy val compilerOptions = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-encoding", "UTF-8",
    "-feature",
    "-Ywarn-dead-code",
    "-Ywarn-unused",
    "-Xcheckinit",
    "-Xlint",
    "-Xfatal-warnings"
  )
)

lazy val dependencies = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % catsV,
    "org.scalatest" %% "scalatest" % scalatestV % Test,
  )
)

lazy val root = (project in file("."))
  .settings(name := "desert-mouse")
  .settings(compilerOptions)
  .settings(dependencies)
