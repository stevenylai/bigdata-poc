import org.junit.Test

class YamlSuite {
  @Test def `parse yaml`: Unit = {
    val config = Main.getConfig()
    val res = config.hcursor
      .downField("bar")
      .downField("baz")
      .downField("one")
      .as[String].getOrElse("aa")
    //println(config)
    println(res)
  }
  @Test def `interpolation`: Unit = {
    val path = Env.expand("xxxx$PATH/xxxx$abc/aer")
    println(path)
    println(Env.expand("$xxxx"))
  }
}
