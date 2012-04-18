package egg.demo.mongodemo.dao;

import egg.demo.mongodemo.beans.RunningTotal;
import java.util.Iterator;

public interface SearchDao {
    public Iterator<RunningTotal> findMobile();
}
