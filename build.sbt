
name := "graal-examples"
scalaVersion := "2.12.7"

// https://mvnrepository.com/artifact/org.graalvm.sdk/graal-sdk
libraryDependencies += "org.graalvm.sdk" % "graal-sdk" % "19.0.0"

// https://mvnrepository.com/artifact/org.graalvm.js/js
libraryDependencies += "org.graalvm.js" % "js" % "19.0.0"

// https://mvnrepository.com/artifact/org.graalvm.truffle/truffle-api
libraryDependencies += "org.graalvm.truffle" % "truffle-api" % "19.0.0"

lazy val testingDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.0.+",
  "org.scalatest" %% "scalatest" % "3.0.+" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.+" % "test"
)

libraryDependencies ++= testingDependencies

scalacOptions ++= Seq("-feature", "-deprecation")

parallelExecution := true

fork := true
