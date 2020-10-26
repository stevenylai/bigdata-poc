import java.util.regex.Matcher

import scala.annotation.tailrec
import scala.util.matching.Regex

object Env {
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
          case Some(matched) => {
            val expanded = System.getenv(matched.group(0))
            if (expanded == null) key else expanded
          }
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
