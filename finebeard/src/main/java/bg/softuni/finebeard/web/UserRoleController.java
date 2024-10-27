package bg.softuni.finebeard.web;

import bg.softuni.finebeard.model.dto.UserEmailRolesDTO;
import bg.softuni.finebeard.model.dto.UserRoleManagementDTO;
import bg.softuni.finebeard.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * This controller handles user role management functions, allowing users
 * to be granted or revoked admin privileges.
 */
@Controller
@RequestMapping("/roles")
public class UserRoleController {
    /**
     * Service providing user management functionalities, including role management,
     * user registration and authentication.
     */
    private final UserService userService;

    /**
     * Constructs a new UserRoleController with the given UserService.
     *
     * @param userService the UserService used to manage user roles
     */
    public UserRoleController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Manages the roles of users by providing user details and roles for display.
     * This method adds necessary attributes to the model for displaying user roles
     * in the management view.
     *
     * @param pageable the pagination information
     * @param model the model to which attributes are added
     * @return the name of the view to be rendered
     */
    @GetMapping("/management")
    public String manageRoles(Pageable pageable, Model model) {

        if (!model.containsAttribute("userRoleManagementDTO")) {
            model.addAttribute("userRoleManagementDTO", new UserRoleManagementDTO(""));
        }


        Page<UserEmailRolesDTO> usersNamesAndRoles = userService.getAllUsersNamesAndRoles(pageable);

        model.addAttribute("usersNamesAndRoles", usersNamesAndRoles);

        return "user-role-management";
    }

    /**
     * Adds an admin role to a user based on the provided userRoleManagementDTO.
     *
     * @param userRoleManagementDTO Data transfer object containing the user's email.
     * @param bindingResult The binding result holding the validation results of the userRoleManagementDTO.
     * @param rAtt RedirectAttributes to pass flash attributes used for redirection.
     * @return A redirect string to the role management page if successful, or to the management page if there are validation errors.
     */
    @PostMapping("/management/addAdmin")
    public String addAdmin(@Valid UserRoleManagementDTO userRoleManagementDTO, BindingResult bindingResult,
                           RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userRoleManagementDTO", userRoleManagementDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userRoleManagementDTO", bindingResult);
            return "redirect:/management";
        }

        userService.addUserRole(userRoleManagementDTO.username());


        return "redirect:/roles/management";
    }


    /**
     * Removes the admin role from a user.
     *
     * @param userRoleManagementDTO a data transfer object containing the username of the user whose admin role is to be removed
     * @param bindingResult the result of the validation of the userRoleManagementDTO
     * @param rAtt attributes for a redirect scenario
     * @return a redirect URL to the role management page if successful or to the management page if validation errors occur
     */
    @PostMapping("/management/removeAdmin")
    public String removeAdmin(@Valid UserRoleManagementDTO userRoleManagementDTO,
                              BindingResult bindingResult,
                              RedirectAttributes rAtt) {

        if (bindingResult.hasErrors()) {
            rAtt.addFlashAttribute("userRoleManagementDTO", userRoleManagementDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userRoleManagementDTO", bindingResult);
            return "redirect:/management";
        }

        userService.removeUserRole(userRoleManagementDTO.username());

        return "redirect:/roles/management";
    }
}
