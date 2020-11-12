
name := "graal-scala-examples"
scalaVersion := "2.12.7"

val graalVersion = "19.3.1"

// https://mvnrepository.com/artifact/org.graalvm.sdk/graal-sdk
libraryDependencies += "org.graalvm.sdk" % "graal-sdk" % graalVersion

// https://mvnrepository.com/artifact/org.graalvm.js/js
libraryDependencies += "org.graalvm.js" % "js" % graalVersion

// https://mvnrepository.com/artifact/org.graalvm.truffle/truffle-api
libraryDependencies += "org.graalvm.truffle" % "truffle-api" % graalVersion

//https://mvnrepository.com/artifact/org.graalvm.truffle/truffle-dsl-processor
libraryDependencies += "org.graalvm.truffle" % "truffle-dsl-processor" % graalVersion

// https://mvnrepository.com/artifact/org.graalvm.tools/profiler
libraryDependencies += "org.graalvm.tools" % "profiler" % "19.0.2"

lazy val testingDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.0.+",
  "org.scalatest" %% "scalatest" % "3.0.+" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.+" % "test"
)

libraryDependencies ++= testingDependencies

scalacOptions ++= Seq("-feature", "-deprecation")

javaOptions ++= Seq(
  "-XX:+UnlockExperimentalVMOptions",
  "-XX:+EnableJVMCI",
  "-XX:+UseJVMCICompiler",
  "-Dtruffle.class.path.append=lib/graal-java-examples-1.0.jar"
)

parallelExecution := true

fork := true
