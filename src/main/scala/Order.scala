object Order {
  trait OrderTraits {
    def instance: String
    def spolRef: Int
    def trader: String
  }
  case class Order(instance: String,
                   spolRef: Int,
                   trader: String) extends OrderTraits
  case class Execution(instance: String,
                       spolRef: Int,
                       trader: String) extends OrderTraits
}
