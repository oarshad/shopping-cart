name := "scala-shopping-cart"

version := "0.1"

scalaVersion := "2.13.7"

val catsEffectVersion = "3.3.0"
val munitCatsEffectVersion = "1.0.7"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % catsEffectVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.6",
  // Test dependencies
  "org.typelevel" %% "munit-cats-effect-3" % munitCatsEffectVersion % Test
)

fork := true

run / connectInput := true
