package com.mybooks.controllers;

import com.mybooks.model.entities.User;
import com.mybooks.services.ProfileService;
import com.mybooks.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    public static final String REDIRECT_REGISTRATION = "redirect:/registration";
    public static final String REDIRECT_LOGIN = "redirect:/login";
    private UserService userService;
    private ProfileService profileService;

    @PostMapping("/save")
    public String save(@RequestParam String name,
                       @RequestParam String surname,
                       @RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String repeatPassword,
                       RedirectAttributes ra) {
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
