package com.mybooks.controllers;

import com.mybooks.model.entities.Role;
import com.mybooks.model.entities.Status;
import com.mybooks.model.entities.User;
import com.mybooks.services.ProfileService;
import com.mybooks.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    public static final String PAGES_ADMIN = "pages/main";
    public static final String PAGES_USER = "pages/user";
    public static final String REDIRECT_ADMIN = "redirect:/admin";
    private ProfileService profileService;
    private UserService userService;


    @GetMapping()
    public String getUsers(Model model) {
        model.addAttribute("nav", "admin");
        model.addAttribute("profiles", profileService.getAllByOrderBySurname());
        return PAGES_ADMIN;
    }

    @GetMapping("/search")
    public String getUsers(Model model, @RequestParam String search) {
        model.addAttribute("nav", "admin");
        model.addAttribute("search", search);
        model.addAttribute("profiles", profileService.getAllByUsername(search));
        return PAGES_ADMIN;
    }


    @GetMapping("/user/{id}")
    public String getUser(Model model, @PathVariable Integer id) {
        model.addAttribute("nav", "admin");
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("statuses", Status.values());
        return PAGES_USER;
    }

    @PostMapping("/user/update/{id}")
    public String update(@PathVariable Integer id,
                             @RequestParam String strRole,
                             @RequestParam String strStatus) {
        User user = userService.getById(id);
        Role role = Role.valueOf(strRole);
        Status status = Status.valueOf(strStatus);
        userService.update(user, role, status);
        return REDIRECT_ADMIN;
    }

    @PostMapping("/user/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.delete(id);
        return REDIRECT_ADMIN;
    }
}
