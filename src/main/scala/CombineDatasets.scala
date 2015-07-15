object CombineDatasets extends App with CountryAnalyzer with CountryAnalyzerConfig {

    override val envName: String = "cluster"

    topElectricityProducers(year = 2005, number = 5).collect().foreach(println)

    sc.stop()
}
