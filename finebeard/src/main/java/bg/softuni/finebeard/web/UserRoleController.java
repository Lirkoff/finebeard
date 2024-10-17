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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/roles")
public class UserRoleController {
    private final UserService userService;

    public UserRoleController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/management")
    public String manageRoles(Pageable pageable, Model model) {

        if (!model.containsAttribute("userRoleManagementDTO")) {
            model.addAttribute("userRoleManagementDTO", new UserRoleManagementDTO(""));
        }


        Page<UserEmailRolesDTO> usersNamesAndRoles = userService.getAllUsersNamesAndRoles(pageable);

        model.addAttribute("usersNamesAndRoles", usersNamesAndRoles);

        return "user-role-management";
    }

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


    @PostMapping("/management/removeAdmin")
    public String removeAdmin(@Valid UserRoleManagementDTO userRoleManagementDTO, BindingResult bindingResult,
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
