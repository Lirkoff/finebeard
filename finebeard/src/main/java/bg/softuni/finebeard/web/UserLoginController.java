package bg.softuni.finebeard.web;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling user login-related requests.
 */
@Controller
@RequestMapping("/users")
public class UserLoginController {



    /**
     * Handles GET requests for the user login page.
     *
     * @return The view name for the login page, which is "auth-login".
     */
    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }


    /**
     * Handles login error by adding an attribute to the model indicating bad credentials.
     *
     * @param model the model to which the "bad_credentials" attribute is added
     * @return the view name "auth-login" to be rendered
     */
    @PostMapping("/login-error")
    public String onFailure(Model model) {
        model.addAttribute("bad_credentials", true);

        return "auth-login";
    }

    /**
     * Handles requests to indicate that an email is required for login or other user actions.
     *
     * @return The path to the "email-required" error page.
     */
    @GetMapping("/email-required")
    public String emailRequired() {

        return "error/email-required";
    }

    /**
     * Handles the request to check if an email already exists.
     *
     * @return the view name for the existing user error page
     */
    @GetMapping("/existing-user")
    public String emailExists() {

        return "error/existing-user";
    }


    /**
     * Logs the user out and redirects to the home page.
     *
     * @return a string specifying the redirect URL to the home page.
     */
    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
