name := "spark-combine-datasets-test"

version := "1.0"

scalaVersion := "2.10.4"


libraryDependencies += "org.apache.spark" %% "spark-core" % "1.4.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.4.0"
libraryDependencies += "com.typesafe" % "config" % "1.3.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.5" % "test"