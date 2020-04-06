import Dependencies._

name := "flowMan"
version := "0.1"
scalaVersion := "2.13.1"


lazy val main = Project(id = "main", base = file("main"))
.settings(libraryDependencies ++= fs2 ++ circe ++ akkaHttp ++ catsEffects)