import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object SaveToES {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SaveToES")
    conf.set("es.index.auto.create", "true")
    conf.set("es.net.http.auth.user", "elastic")
    conf.set("es.net.http.auth.pass", "changeme")
    
    val sc = new SparkContext(conf)

    val base = "/projects/chigun-index/resources/txt/"
    val f = "1991_1.htm.txt"
    val fp = base + f
    val textFile = sc.textFile(fp)

    // OR

    val files = sc.wholeTextFiles("/projects/chigun-index/resources/txt/*")

    // And maybe after:
    sc.makeRDD(files).saveToEs("chigun/issues")

    textFile
	.flatMap(line => line.split(" "))
	.filter(_.length>3)
	.map(_.toLowerCase)
	.map(word => (word, 1))
	.reduceByKey((a, b) => a + b)
	.sortBy(_._2)
    .collect().foreach(println)

    sc.stop()
  }
}
