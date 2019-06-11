import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

lazy val catsVersion = "1.6.1"

// Due to the cross project being in(file(".")), we need to prevent
// the automatic root project from compiling.
Compile / unmanagedSourceDirectories := Nil
Test / unmanagedSourceDirectories := Nil
publishArtifact := false

lazy val `cats-scalatest` = crossProject(JVMPlatform, JSPlatform)
  .withoutSuffixFor(JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .settings(
    name := "cats-scalatest",
    organization := "com.ironcorelabs",

    scalaVersion := "2.12.8",
    crossScalaVersions := Seq("2.11.12", "2.12.8"),

    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked"
    ),

    resolvers += Resolver.sonatypeRepo("releases"),

    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % catsVersion,
      "org.typelevel" %%% "cats-macros" % catsVersion,
      "org.scalatest" %%% "scalatest" % "3.0.8"
    )
  )

