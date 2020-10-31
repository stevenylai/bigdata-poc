import java.io.InputStreamReader

import io.circe.{Json, ParsingFailure}
import io.circe.yaml.parser
import org.apache.spark.rdd.RDD

object Main {
  val sparkConfig = new SparkConfig(getClass.getClassLoader.getResourceAsStream("data.yml"))
  val sparkConnection = new SparkConnection(true)

  def getStringRddFromResources() = {
    sparkConnection.context.textFile("src/main/resources/orders.json")
  }
  def process(rdd: RDD[String]) = {
    val orders = rdd.map{
      line => parser.parse(line) match {
        case Right(v) => {
          for {
            instance <- v.hcursor.downField("0").as[String].right
            orderId <- v.hcursor.downField("1").as[Int].right
            trader <- v.hcursor.downField("2").as[String].right
          } yield {
            Order.Order(instance, orderId, trader)
          }
        } match {
          case Right(order) => Some(order)
          case _ => None
        }
        case _ => None
      }
    }
  }
}
