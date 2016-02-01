name := "cats-scalatest"
organization := "com.ironcorelabs"

scalaVersion := "2.11.7"
crossScalaVersions := Seq("2.10.5", "2.11.7")

com.typesafe.sbt.SbtScalariform.scalariformSettings

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked"
)

resolvers += Resolver.sonatypeRepo("releases")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "0.4.0",
  "org.typelevel" %% "cats-macros" % "0.4.0",
  "org.scalatest" %% "scalatest" % "2.2.4"
)
