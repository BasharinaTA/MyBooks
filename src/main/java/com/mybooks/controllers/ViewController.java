package com.mybooks.controllers;

import com.mybooks.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class ViewController {

    public static final String PAGES_HOME = "pages/home";
    public static final String PAGES_LOGIN = "pages/login";
    public static final String PAGES_PROFILE = "pages/profile";
    private ProfileService profileService;

    @GetMapping
    public String getHome() {
        return PAGES_HOME;
    }

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(required = false) String username,
                        HttpServletRequest request) {
        model.addAttribute("login", true);
        model.addAttribute("nav", "login");
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("errorAuth", true);
        }
        return PAGES_LOGIN;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("login", false);
        model.addAttribute("nav", "registration");
        return PAGES_LOGIN;
    }


    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        model.addAttribute("profile", profileService.getProfileByPrincipal(principal));
        model.addAttribute("nav", "profile");
        return PAGES_PROFILE;
    }
}
