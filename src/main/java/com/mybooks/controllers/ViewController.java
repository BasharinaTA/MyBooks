package com.mybooks.controllers;

import com.mybooks.model.entities.User;
import com.mybooks.services.ProfileService;
import com.mybooks.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

import static com.mybooks.controllers.ProfileController.REDIRECT_PROFILES;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class ViewController {

    public static final String PAGES_HOME = "pages/home";
    public static final String PAGES_LOGIN = "pages/login";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    public static final String REDIRECT_REGISTRATION = "redirect:/registration";

    private UserService userService;
    private ProfileService profileService;

    @GetMapping
    public String getHome(Principal principal) {
        if (principal != null) {
            return REDIRECT_PROFILES;
        }
        return PAGES_HOME;
    }

    @GetMapping("/login")
    public String getLogin(Principal principal, Model model) {
        if (principal != null) {
            return REDIRECT_PROFILES;
        }
        model.addAttribute("nav", "login");
        return PAGES_LOGIN;
    }

    @GetMapping("/login-error")
    public String getLoginError(Principal principal, Model model) {
        if (principal != null) {
            return REDIRECT_PROFILES;
        }
        model.addAttribute("nav", "login");
        model.addAttribute("loginError", true);
        return PAGES_LOGIN;
    }

    @GetMapping("/registration")
    public String getRegistration(Principal principal, Model model) {
        if (principal != null) {
            return REDIRECT_PROFILES;
        }
        model.addAttribute("nav", "registration");
        return PAGES_LOGIN;
    }

    @PostMapping
    public String addProfile(@RequestParam String username, @RequestParam String password,
                             @RequestParam String repeatPassword, @RequestParam String name,
                             @RequestParam String surname, RedirectAttributes ra) {
        Optional<User> user = userService.getByUser(username);
        if (user.isPresent() || !password.equals(repeatPassword)) {
            if (user.isPresent()) {
                ra.addFlashAttribute("error", "username");
            } else {
                ra.addFlashAttribute("error", "password");
            }
            ra.addFlashAttribute("name", name);
            ra.addFlashAttribute("surname", surname);
            ra.addFlashAttribute("username", username);
            return REDIRECT_REGISTRATION;
        }
        profileService.save(username, password, name, surname);
        return REDIRECT_LOGIN;
    }
}
