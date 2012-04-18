package egg.demo.mongodemo.dao;

import egg.demo.mongodemo.beans.LogEvent;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface LogEventDao extends CrudRepository<LogEvent, String>  {
    List<LogEvent> findBySource(String source);
}
