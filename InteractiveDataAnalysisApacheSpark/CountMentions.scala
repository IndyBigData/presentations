val lines = sc.textFile("/some/location/wikilinks/data*")
val mentions = lines.filter(l => l.startsWith("MENTION"))
val mentionCounts = mentions.map{l => val txt = l.split("\t")(1); (txt, 1)}.reduceByKey((a, b) => a + b, 8)
val sortedMentions = mentionCounts.map{case (k, v) => (v, k)}.sortByKey(false, 8)
sortedMentions.take(10).foreach(println _)