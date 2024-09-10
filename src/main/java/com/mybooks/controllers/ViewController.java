package com.mybooks.controllers;

import com.mybooks.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class ViewController {

    public static final String PAGES_HOME = "pages/home";
    public static final String PAGES_LOGIN = "pages/login";
    public static final String PAGES_PROFILE = "pages/profile";
    public static final String REDIRECT_PROFILE = "redirect:/profile";
    private ProfileService profileService;

    @GetMapping
    public String getHome(Principal principal) {
        if (principal != null) {
            return REDIRECT_PROFILE;
        }
        return PAGES_HOME;
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request, Principal principal) {
        if (principal != null) {
            return REDIRECT_PROFILE;
        }
        model.addAttribute("nav", "login");
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("errorAuth", true);
        }
        return PAGES_LOGIN;
    }

    @GetMapping("/registration")
    public String getRegistration(Model model, Principal principal) {
        if (principal != null) {
            return REDIRECT_PROFILE;
        }
        model.addAttribute("nav", "registration");
        return PAGES_LOGIN;
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        model.addAttribute("nav", "profile");
        model.addAttribute("profile", profileService.getProfileByPrincipal(principal));
        return PAGES_PROFILE;
    }
}
