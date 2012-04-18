package egg.demo.mongodemo.controllers;

import egg.demo.mongodemo.beans.LogEvent;
import egg.demo.mongodemo.dao.LogEventDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogEventController {
    @Resource
    LogEventDao logEventDao;
    
    @RequestMapping("/showLogEvents")
    public String showLogEvents(Model model) {
        Iterable<LogEvent> logEvents = logEventDao.findBySource("RecordRequestController");
        Long count = logEventDao.count();
        
        model.addAttribute("logEvents", logEvents);
        model.addAttribute("count", count);
        return "logEvent";
    }
}
