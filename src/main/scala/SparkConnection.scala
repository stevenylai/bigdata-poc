import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

class SparkConnection (localCluster: Boolean) {
  lazy val conf: SparkConf = new SparkConf()
    .setMaster("local")
    .setAppName("MyApplication")
  lazy val context: SparkContext = new SparkContext(conf)
  lazy val session: SparkSession = getSparkSession()

  def getSparkSession() = {
    val builder = SparkSession
    .builder().config(conf)
    .config("spark.sql.warehouse.dir", new java.io.File("spark-warehouse").getAbsolutePath)
    if (!localCluster) builder.enableHiveSupport()
    builder.getOrCreate()
  }


}
