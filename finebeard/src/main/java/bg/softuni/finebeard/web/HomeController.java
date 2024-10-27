package bg.softuni.finebeard.web;

import bg.softuni.finebeard.service.HomepageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The HomeController is a Spring MVC controller responsible for handling
 * HTTP requests to the home, about, and help pages of the application.
 */
@Controller
public class HomeController {

    /**
     * Service used to manage and retrieve products displayed on the homepage.
     * Provides methods to get and update the list of products.
     */
    private final HomepageService homepageService;

    /**
     * Constructs a new HomeController with the specified HomepageService.
     *
     * @param homepageService the service used to manage homepage-related operations
     */
    public HomeController(HomepageService homepageService) {
        this.homepageService = homepageService;
    }

    /**
     * Handles HTTP GET request for the home page.
     *
     * @param model the model to which homepage products are added as an attribute
     * @return the name of the view to be rendered, in this case "index"
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", homepageService.getHomepageProducts());

        return "index";
    }



    /**
     * Handles GET requests to the "/about" endpoint and returns the name of the
     * view for the about page.
     *
     * @return The name of the view to be rendered, "about".
     */
    @GetMapping("/about")
    public String about() {
        return "about";
    }

    /**
     * Handles HTTP GET requests for the help page.
     *
     * @return a String representing the name of the view to be rendered for the help page.
     */
    @GetMapping("/help")
    public String help() {
        return "help";
    }



}
