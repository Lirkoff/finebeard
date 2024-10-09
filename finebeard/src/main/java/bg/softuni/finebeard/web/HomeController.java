package bg.softuni.finebeard.web;

import bg.softuni.finebeard.service.HomepageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final HomepageService homepageService;

    public HomeController(HomepageService homepageService) {
        this.homepageService = homepageService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", homepageService.getHomepageProducts());
        return "index";
    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }



}
