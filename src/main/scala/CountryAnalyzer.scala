import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext}

trait CountryAnalyzer {

  implicit val sc: SparkContext
  implicit val sqlContext: SQLContext

  val countriesFileUrl: String
  val dataFileUrl: String

  def topElectricityProducers(year: Int, number: Int): RDD[String] = {

    dataFrameFromCsv(countriesFileUrl, "countries")
    dataFrameFromCsv(dataFileUrl, "data")

    val countries = sqlContext.sql(
       s"""
         |SELECT `Country Code`, `Short Name`, `Long Name`, `Region`
         |FROM countries
         |WHERE Region != ''
        """.stripMargin)

    sqlContext.udf.register("toNumber", (x: String) => x.toDouble)
    val data = sqlContext.sql(
       s"""
         |SELECT `Country Code`, toNumber(`$year`) as `Consumption`
         |FROM data
         |WHERE `Indicator Name` = 'Electricity production (kWh)' AND `$year` != ''
        """.stripMargin)

    val joined = data.join(countries, "Country Code").drop("Country Code")
    val resultDf = joined.sort(joined("Consumption").desc).limit(number)

    resultDf.map(r => s"${r(1)}: ${r(0)} (${r(2)}, ${r(3)})")
      .zipWithIndex().map { case (str, idx) => s"${idx + 1}. $str" }
  }

  private def dataFrameFromCsv(csvFileUrl: String, dfName: String)
                      (implicit sc: SparkContext, sqlContext: SQLContext): DataFrame = {
    val csvFile = sc.textFile(csvFileUrl)
    val schemaStr = csvFile.first()

    import org.apache.spark.sql.types._
    val schema = StructType(schemaStr.split("\\|", -1)
      .map(fieldName => StructField(fieldName, StringType, nullable = true)))

    import org.apache.spark.sql.Row
    val rdd = csvFile.mapPartitionsWithIndex {
      (idx, part) => if (idx == 0) part.drop(1) else part
    }.map(_.split("\\|", -1)).map(c => Row(c: _*))

    val dF = sqlContext.createDataFrame(rdd, schema)
    dF.registerTempTable(dfName)
    dF
  }
}
