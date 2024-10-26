package bg.softuni.finebeard.web;


import bg.softuni.finebeard.model.dto.ReCaptchaResponseDTO;
import bg.softuni.finebeard.model.dto.UserRegistrationDTO;
import bg.softuni.finebeard.service.ReCaptchaService;
import bg.softuni.finebeard.service.UserActivationService;
import bg.softuni.finebeard.service.UserService;
import bg.softuni.finebeard.service.exception.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RequestMapping("/users")
@Controller
public class UserRegistrationController {

    private final UserService userService;
    private final ReCaptchaService reCaptchaService;
    private final UserActivationService userActivationService;

    public UserRegistrationController(UserService userService, ReCaptchaService reCaptchaService,UserActivationService userActivationService) {
        this.userService = userService;
        this.reCaptchaService = reCaptchaService;
        this.userActivationService = userActivationService;
    }

    @GetMapping("/register")
    public String register(Model model) {

        if (!model.containsAttribute("userRegistrationDTO")) {
            model.addAttribute("userRegistrationDTO", UserRegistrationDTO.empty());
        }
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userRegistrationDTO") UserRegistrationDTO userRegistrationDTO,
                           BindingResult bindingResult,
                           RedirectAttributes rAtt,
                           @RequestParam("g-recaptcha-response") String reCaptchaResponse) {

        boolean isBot = !reCaptchaService
                .verify(reCaptchaResponse)
                .map(ReCaptchaResponseDTO::isSuccess)
                .orElse(false);

        if (isBot) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()){
            rAtt.addFlashAttribute("userRegistrationDTO",userRegistrationDTO);
            rAtt.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);
            return "redirect:/users/register";
        }

        userService.registerUser(userRegistrationDTO);
        return "redirect:/";
    }

    @GetMapping("/activate/{activationCode}")
    public String login(@PathVariable String activationCode,
                        HttpServletRequest request,
                        Model model) {

        String ipAddress = request.getRemoteAddr();

        try {
            boolean isActivated = userActivationService.activateUser(activationCode, ipAddress);

            if (isActivated) {
                model.addAttribute("message", "Your account was activated successfully!");
            } else {
                model.addAttribute("error", "Invalid or expired activation code!");
            }
        } catch (RequestNotPermitted ex) {
            model.addAttribute("error", "Too many activation attempts. Please try again later.");
        }


        return "activation-result";

    }

}
