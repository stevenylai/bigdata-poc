import org.junit.Test

class YamlSuite {
  @Test def `parse yaml`: Unit = {
    val config = Main.sparkConfig
    val res = config.settings.hcursor
      .downField("bar")
      .downField("baz")
      .downField("one")
      .as[String].right.get
    //println(config)
    println(res)
  }
  @Test def `interpolation`: Unit = {
    val path = Main.sparkConfig.expand("xxxx$PATH/xxxx$abc/aer")
    println(path)
    println(Main.sparkConfig.expand("$xxxx"))
  }
}
