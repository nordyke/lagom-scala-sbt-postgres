organization in ThisBuild := "org.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
val postgres = "org.postgresql" % "postgresql" % "42.2.7"


lazy val `hello` = (project in file("."))
  .aggregate(`hello-api`, `hello-impl`, `hello-stream-api`, `hello-stream-impl`)

lazy val `hello-api` = (project in file("hello-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `hello-impl` = (project in file("hello-impl"))
  .enablePlugins(LagomScala)
  .settings(
    lagomCassandraEnabled in ThisBuild := false,
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceJdbc,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      postgres,
      "ch.qos.logback" % "logback-classic" % "1.2.3" % Test
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`hello-api`)

lazy val `hello-stream-api` = (project in file("hello-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `hello-stream-impl` = (project in file("hello-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`hello-stream-api`, `hello-api`)
