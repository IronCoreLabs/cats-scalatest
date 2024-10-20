addSbtPlugin("org.scalameta"      % "sbt-scalafmt"             % "2.5.2")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin"    % "1.0.0")
addSbtPlugin("org.scoverage"      % "sbt-scoverage"            % "2.2.2")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.3.2")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.17.0")
addSbtPlugin("com.github.sbt"     % "sbt-ci-release"           % "1.8.0")

// workaround for conflict between sbt-scoverage and scalastyle-sbt-plugin
// https://github.com/scala/bug/issues/12632
ThisBuild / libraryDependencySchemes ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)
