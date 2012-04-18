package egg.demo.mongodemo.controllers;

import egg.demo.mongodemo.beans.RunningTotal;
import egg.demo.mongodemo.dao.SearchDao;
import java.util.Iterator;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {
    @Resource
    SearchDao searchDao;
    
    @RequestMapping(value="/search")
    public String search(Model model) {
        Iterator<RunningTotal> totals = searchDao.findMobile();
        model.addAttribute("totals", totals);
        return "search";
    }
}
