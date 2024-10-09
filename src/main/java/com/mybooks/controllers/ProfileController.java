package com.mybooks.controllers;

import com.mybooks.model.entities.*;
import com.mybooks.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    public static final String PAGES_PROFILE = "pages/profile";
    public static final String REDIRECT_PROFILES = "redirect:/profiles";
    private ProfileService profileService;

    @GetMapping
    public String getProfile(Model model) {
        model.addAttribute("nav", "profile");
        model.addAttribute("profile", profileService.getByUser());
        return PAGES_PROFILE;
    }

    @PostMapping
    public String updateProfile(@RequestParam String name, @RequestParam String surname) {
        Profile profile = profileService.getByUser();
        profileService.update(profile, name, surname);
        return REDIRECT_PROFILES;
    }
}
