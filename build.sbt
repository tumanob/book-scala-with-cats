
lazy val root = project
  .in(file("."))
  .settings(
    name := "book-scala-with-cats",
    version := "0.1.0",

    scalaVersion := "2.13.1",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.1.0",
//    scalacOptions ++= Seq("-Ypartial-unification")
  )
