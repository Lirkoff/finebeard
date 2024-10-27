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


/**
 * Controller for handling user registration and activation.
 * Provides endpoints to register a new user and activate the user account.
 */
@RequestMapping("/users")
@Controller
public class UserRegistrationController {

    /**
     * Service for handling user-related operations.
     * Provides methods to register a new user, add and remove user roles,
     * retrieve users along with their roles, create a new user if it does not exist,
     * log in a user, and change a user's username.
     */
    private final UserService userService;

    /**
     * Service for handling ReCaptcha verification to prevent automated bots from abusing
     * registration and activation endpoints.
     */
    private final ReCaptchaService reCaptchaService;

    /**
     * Service for handling user activation processes.
     * It is responsible for managing user activation tasks, such as generating activation codes,
     * activating user accounts based on received activation codes, and cleaning up obsolete activation data.
     */
    private final UserActivationService userActivationService;

    /**
     * Constructor for UserRegistrationController.
     *
     * @param userService Service responsible for managing user-related operations.
     * @param reCaptchaService Service for handling reCaptcha validation.
     * @param userActivationService Service for managing user activation and associated processes.
     */
    public UserRegistrationController(UserService userService, ReCaptchaService reCaptchaService,UserActivationService userActivationService) {
        this.userService = userService;
        this.reCaptchaService = reCaptchaService;
        this.userActivationService = userActivationService;
    }

    /**
     * Handles the GET request for the registration page.
     * Adds an empty UserRegistrationDTO to the model if not already present.
     *
     * @param model the Model object to hold attributes for the view
     * @return the name of the registration view template
     */
    @GetMapping("/register")
    public String register(Model model) {

        if (!model.containsAttribute("userRegistrationDTO")) {
            model.addAttribute("userRegistrationDTO", UserRegistrationDTO.empty());
        }
        return "auth-register";
    }

    /**
     * Registers a new user with the provided registration details if the provided reCAPTCHA response
     * is valid and there are no validation errors in the user input.
     * Redirects to the home page if registration is successful or there are issues with the reCAPTCHA response.
     * Redirects back to the registration form if there are validation errors in the user input.
     *
     * @param userRegistrationDTO the DTO containing user registration details
     * @param bindingResult       the result of validation binding
     * @param rAtt                attributes for a redirect scenario
     * @param reCaptchaResponse   the response token from reCAPTCHA verification
     * @return the URL to redirect to
     */
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

    /**
     * Activates a user's account based on the provided activation code.
     *
     * @param activationCode the activation code used to activate the user account
     * @param request the HttpServletRequest object containing the client's request
     * @param model the model to hold attributes for the view
     * @return the view name to be rendered
     */
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
