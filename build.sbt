name := "cats-scalatest"
version := "0.1.0"
organization := "com.ironcorelabs"

scalaVersion := "2.11.7"
crossScalaVersions := Seq("2.10.5", "2.11.7")

com.typesafe.sbt.SbtScalariform.scalariformSettings

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "org.spire-math" %% "cats-core" % "0.2.0",
  "org.spire-math" %% "cats-macros" % "0.2.0",
  "org.scalatest" %% "scalatest" % "2.2.4"
)

resolvers += Resolver.sonatypeRepo("releases")
licenses := Seq("Apache-2.0" → url("http://www.opensource.org/licenses/Apache-2.0"))
homepage := Some(url("http://github.com/ironcorelabs/cats-scalatest"))
