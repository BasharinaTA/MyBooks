package com.mybooks.controllers;

import com.mybooks.model.entities.Role;
import com.mybooks.model.entities.Status;
import com.mybooks.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.mybooks.controllers.BookController.PAGES_MAIN;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    public static final String PAGES_USER = "pages/user";
    public static final String REDIRECT_ADMIN_USERS_SEARCH = "redirect:/admin/users/search";
    private UserService userService;

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("nav", "admin");
        model.addAttribute("users", userService.getAllOrderByName());
        return PAGES_MAIN;
    }

    @GetMapping("/users/search")
    public String getUsers(Model model, @RequestParam(required = false) String search) {
        model.addAttribute("nav", "admin");
        if (search != null) {
            model.addAttribute("search", search);
        }
        model.addAttribute("users",
                userService.getAllByUsernameOrderByName((String) model.getAttribute("search")));
        return PAGES_MAIN;
    }

    @PostMapping("/block/user/{id}")
    public String blockUser(RedirectAttributes ra, @RequestParam String search, @PathVariable Integer id) {
        ra.addFlashAttribute("search", search);
        userService.block(id);
        return REDIRECT_ADMIN_USERS_SEARCH;
    }

    @PostMapping("/unblock/user/{id}")
    public String unblockUser(RedirectAttributes ra, @RequestParam String search, @PathVariable Integer id) {
        ra.addFlashAttribute("search", search);
        userService.unblock(id);
        return REDIRECT_ADMIN_USERS_SEARCH;
    }

    @GetMapping("/user/{id}")
    public String getUser(Model model, @RequestParam String search, @PathVariable Integer id) {
        model.addAttribute("nav", "admin");
        model.addAttribute("search", search);
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", Role.values());
        model.addAttribute("statuses", Status.values());
        return PAGES_USER;
    }

    @PostMapping("/update/user/{id}")
    public String updateUser(RedirectAttributes ra, @RequestParam String search, @PathVariable Integer id,
                             @RequestParam String role, @RequestParam String status) {
        ra.addFlashAttribute("search", search);
        userService.update(id, role, status);
        return REDIRECT_ADMIN_USERS_SEARCH;
    }

    @PostMapping("/delete/user/{id}")
    public String deleteUser(RedirectAttributes ra, @RequestParam String search, @PathVariable Integer id) {
        ra.addFlashAttribute("search", search);
        userService.delete(id);
        return REDIRECT_ADMIN_USERS_SEARCH;
    }
}
