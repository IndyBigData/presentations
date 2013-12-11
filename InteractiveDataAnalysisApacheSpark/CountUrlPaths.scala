import scala.util.Try

val paths = mentions.map{l => (Try(new java.net.URL(l.split("\t")(3)).getPath).getOrElse(null), 1)}.filter(p => p != null)
val pathCounts = paths.reduceByKey((a, b) => a + b, 8)
val sortedPaths = pathCounts.map{case (k, v) => (v, k)}.sortByKey(false, 8)
sortedPaths.take(10).foreach(println _)
