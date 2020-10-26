import java.io.InputStreamReader

import io.circe.{Json, ParsingFailure}
import io.circe.yaml.parser

object Main {
  def getConfig() = {
    val config = getClass.getClassLoader.getResourceAsStream("data.yml")
    parser.parse(new InputStreamReader(config)) match {
      case Right(d) => d
      case Left(e) => throw e
    }
  }

}
