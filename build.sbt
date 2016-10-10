name := "cats-scalatest"
organization := "com.ironcorelabs"

scalaVersion := "2.11.8"
crossScalaVersions := Seq("2.10.5", "2.11.8")

com.typesafe.sbt.SbtScalariform.scalariformSettings

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked"
)

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "0.7.2",
  "org.typelevel" %% "cats-macros" % "0.7.2",
  "org.scalatest" %% "scalatest" % "3.0.0"
)
