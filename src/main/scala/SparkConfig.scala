import java.io.InputStreamReader

import io.circe.ACursor
import io.circe.yaml.parser

import scala.annotation.tailrec
import scala.util.matching.Regex

class SparkConfig(stream: java.io.InputStream) {
  val env = System.getenv()
  val settings = parser.parse(new InputStreamReader(stream)) match {
    case Right(d) => d
    case Left(e) => throw e
  }

  def setEnv(name: String, value: String) = {
    env.put(name, value)
  }
  def getConfigAsString(path: String *) = {
    var cursor: ACursor = settings.hcursor
    val it = path.iterator
    while (it.hasNext) {
      cursor = cursor.downField(it.next())
    }

  }
  def expand(value: String) = {
    val buf = new StringBuilder()
    val pattern = new Regex("\\$[a-zA-Z_][a-zA-Z_0-9]*")
    val matcher = pattern.pattern.matcher(value)

    @tailrec
    def helper(index: Int): Unit = {
      val found = matcher.find(index)
      if (found) {
        buf.append(value.substring(index, matcher.start()))
        val key = matcher.group()
        val namePattern = new Regex("[a-zA-Z_][a-zA-Z_0-9]*")
        val varValue = namePattern.findFirstMatchIn(key) match {
          case Some(matched) => env.getOrDefault(matched.group(0), key)
          case _ => key
        }
        buf.append(varValue)
        helper(matcher.end())
      } else {
        buf.append(value.substring(index))
      }
    }
    helper(0)
    buf.toString()
  }
}
