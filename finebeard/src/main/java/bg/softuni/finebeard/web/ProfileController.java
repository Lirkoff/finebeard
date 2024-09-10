package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.dto.ChangeUsernameDTO;
import bg.softuni.finebeard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;



@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService, UserDetailsService fineBeardUserDetailsService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        if (!model.containsAttribute("changeUsernameDTO")) {
            model.addAttribute("changeUsernameDTO", ChangeUsernameDTO.empty());
        }


        return "profile";
    }

    @PostMapping("/profile/change-username")
    public String changeUsername(Principal principal,
                                 @Valid ChangeUsernameDTO changeUsernameDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("changeUsernameDTO", changeUsernameDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.changeUsernameDTO", bindingResult);
            return "redirect:/profile";
        }

        userService.changeUsername(principal.getName(), changeUsernameDTO.newEmail());

        userService.login(changeUsernameDTO.newEmail());



        return "redirect:/profile";
    }
}
