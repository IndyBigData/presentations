package egg.demo.mongodemo.dao.impl;

import egg.demo.mongodemo.beans.RunningTotal;
import egg.demo.mongodemo.dao.SearchDao;
import java.util.Iterator;
import javax.annotation.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Repository;

@Repository("searchDao")
public class SearchMapReduceDao implements SearchDao {
    @Resource
    protected MongoTemplate mongoTemplate;

    @Override
    public Iterator<RunningTotal> findMobile() {
        MapReduceResults<RunningTotal> results = mongoTemplate.mapReduce(
                "logEvent", "classpath:map.js", "classpath:reduce.js", RunningTotal.class);
        return results.iterator();
    }
}
