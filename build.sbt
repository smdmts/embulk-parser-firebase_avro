lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.11",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "embulk-parser-firebase_avro"
  )

enablePlugins(ScalafmtPlugin)

resolvers += Resolver.jcenterRepo
resolvers += Resolver.sonatypeRepo("releases")


lazy val circeVersion = "0.8.0"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4",
  "org.jruby" % "jruby-complete" % "1.6.5",
  "org.embulk" % "embulk-core" % "0.8.22",
  "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test,
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.5" % Test
)