name := "bigdata-poc"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1"
libraryDependencies += "io.circe" %% "circe-yaml" % "0.12.0"
libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % Test