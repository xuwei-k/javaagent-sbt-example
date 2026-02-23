lazy val core = project
  .settings(
    scalaVersion := "3.8.1",
    libraryDependencies += "org.scalatest" %% "scalatest-freespec" % "3.2.19" % Test,
    Test / fork := true,
    Test / javaOptions += {
      val arg = "java-agent-arg"
      val converter = fileConverter.value
      val agentJar = converter.toPath((agent / Compile / packageBin).value).toFile.getCanonicalPath
      s"-javaagent:${agentJar}=${arg}"
    }
  )

lazy val agent = project
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
    packageOptions += Package.ManifestAttributes(
      "Premain-Class" -> "example.JavaAgentMain"
    )
  )
