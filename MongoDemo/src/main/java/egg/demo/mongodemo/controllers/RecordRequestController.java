
package egg.demo.mongodemo.controllers;

import egg.demo.mongodemo.beans.LogEvent;
import egg.demo.mongodemo.dao.LogEventDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecordRequestController {
    @Resource
    LogEventDao logEventDao;
    
    @RequestMapping("/test")
    public String recordPageRequest(@RequestHeader("User-Agent") String userAgent, Model model) {
        LogEvent event = new LogEvent();
        event.setMessage(userAgent);
        event.setSource(this.getClass().getSimpleName());
        
        logEventDao.save(event);
        
        model.addAttribute("logEvent", event);
        return "recorded";
    }
}
