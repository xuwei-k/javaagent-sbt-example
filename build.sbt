lazy val core = project
  .settings(
    scalaVersion := "3.6.2",
    libraryDependencies += "org.scalatest" %% "scalatest-freespec" % "3.2.19" % Test,
    Test / fork := true,
    libraryDependencies += "org.javassist" % "javassist" % "3.30.2-GA",
    Test / javaOptions += {
      val agentJar = (agent1 / Compile / packageBin).value.getCanonicalPath
      s"-javaagent:${agentJar}"
    },
    Test / javaOptions += {
      val agentJar = (agent2 / Compile / packageBin).value.getCanonicalPath
      s"-javaagent:${agentJar}"
    }
  )

lazy val agent1 = project
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
    libraryDependencies += "org.javassist" % "javassist" % "3.30.2-GA",
    packageOptions += Package.ManifestAttributes(
      "Premain-Class" -> "example.JavaAgentMain1"
    )
  )

lazy val agent2 = project
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
    libraryDependencies += "org.javassist" % "javassist" % "3.30.2-GA",
    packageOptions += Package.ManifestAttributes(
      "Premain-Class" -> "example.JavaAgentMain2"
    )
  )
