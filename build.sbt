enablePlugins(ScalafmtPlugin)

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.example",
      scalaVersion := "2.11.11",
      version := "0.1.0-SNAPSHOT"
    )),
  name := "embulk-parser-firebase_avro",
  scalafmtOnCompile in ThisBuild := true,
  scalafmtTestOnCompile in ThisBuild := true
)

enablePlugins(ScalafmtPlugin)

resolvers += Resolver.jcenterRepo
resolvers += Resolver.sonatypeRepo("releases")

lazy val circeVersion = "0.8.0"
libraryDependencies ++= Seq(
  "com.sksamuel.avro4s"        %% "avro4s-core"                 % "1.8.3",
  "org.jruby"                  % "jruby-complete"               % "1.6.5",
  "org.embulk"                 % "embulk-core"                  % "0.9.7",
  "com.chuusai"                %% "shapeless"                   % "2.3.2",
  "io.circe"                   %% "circe-core"                  % circeVersion,
  "io.circe"                   %% "circe-generic"               % circeVersion,
  "org.scalacheck"             %% "scalacheck"                  % "1.13.4" % Test,
  "org.scalatest"              %% "scalatest"                   % "3.0.1" % Test,
  "org.scalamock"              %% "scalamock-scalatest-support" % "3.6.0" % Test,
  "com.github.alexarchambault" %% "scalacheck-shapeless_1.13"   % "1.1.5" % Test
)
