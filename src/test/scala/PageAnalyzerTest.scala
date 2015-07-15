import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

class PageAnalyzerTest extends FlatSpec with Matchers with BeforeAndAfterAll with CountryAnalyzer with CountryAnalyzerConfig {

  override val envName: String = "local"

  val expected = Seq(
    "1. United States: 4.268887E12 (United States of America, North America)",
    "2. China: 2.502498E12 (People's Republic of China, East Asia & Pacific)",
    "3. Japan: 1.08991E12 (Japan, East Asia & Pacific)",
    "4. Russia: 9.51159E11 (Russian Federation, Europe & Central Asia)",
    "5. India: 6.98249E11 (Republic of India, South Asia)"
  )

  it should "return correct list of top countries" in {
    topElectricityProducers(year = 2005, number = 5).collect().toSeq should equal(expected)
  }

  override def afterAll() = sc.stop()
}
