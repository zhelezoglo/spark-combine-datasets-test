import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

trait CountryAnalyzerConfig {
  self: CountryAnalyzer =>

  val envName: String

  lazy val appName = "combine-datasets"
  lazy val confPathPrefix = s"$envName.$appName.countries-analyzer"
  lazy val appConf = ConfigFactory.load()
  lazy val masterUrl = appConf.getString(s"$confPathPrefix.masterUrl")
  lazy override val countriesFileUrl = appConf.getString(s"$confPathPrefix.countriesFileUrl")
  lazy override val dataFileUrl = appConf.getString(s"$confPathPrefix.dataFileUrl")

  lazy val sparkConf: SparkConf = new SparkConf().setAppName(appName).setMaster(masterUrl)
  lazy override implicit val sc = new SparkContext(sparkConf)
  lazy override implicit val sqlContext = new SQLContext(sc)
}
