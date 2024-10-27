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



/**
 * Controller class responsible for handling profile-related actions.
 */
@Controller
public class ProfileController {

    /**
     * Service for handling user-related operations.
     */
    private final UserService userService;

    /**
     * Constructs a new ProfileController with the given user service and user details service.
     *
     * @param userService the service responsible for user-related operations.
     * @param fineBeardUserDetailsService the service responsible for user details management.
     */
    public ProfileController(UserService userService, UserDetailsService fineBeardUserDetailsService) {
        this.userService = userService;
    }

    /**
     * Handles the GET request for the user profile page.
     *
     * @param model the model to which attributes can be added, typically for rendering the view
     * @return the view name for the profile page
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        if (!model.containsAttribute("changeUsernameDTO")) {
            model.addAttribute("changeUsernameDTO", ChangeUsernameDTO.empty());
        }


        return "profile";
    }

    /**
     * Handles the request to change the username of the currently logged-in user.
     *
     * @param principal        The security principal representing the currently logged-in user.
     * @param changeUsernameDTO  The DTO containing the new email for the username change.
     * @param bindingResult    The binding result that holds any validation errors.
     * @param rAtt             The redirect attributes used to pass flash attributes.
     *
     * @return A redirect URL to the profile page.
     */
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
