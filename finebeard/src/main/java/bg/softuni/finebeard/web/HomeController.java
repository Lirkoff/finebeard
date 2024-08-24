package bg.softuni.finebeard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }



}
