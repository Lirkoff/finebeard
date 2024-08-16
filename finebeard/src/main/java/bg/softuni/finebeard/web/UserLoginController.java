package bg.softuni.finebeard.web;


import bg.softuni.finebeard.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserLoginController {

    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth-login";
    }



    @PostMapping("/login-error")
    public String onFailure(Model model) {
        model.addAttribute("bad_credentials", true);

        return "auth-login";
    }


    @PostMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
