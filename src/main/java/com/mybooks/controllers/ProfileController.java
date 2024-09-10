package com.mybooks.controllers;

import com.mybooks.model.entities.*;
import com.mybooks.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Calendar;
import java.util.GregorianCalendar;


@Controller
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    public static final String PAGES_BOOKS = "pages/main";
    public static final String REDIRECT_PROFILE = "redirect:/profile";
    private ProfileService profileService;

    @GetMapping("/books")
    public String getBooks(Model model, Principal principal) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        Calendar calendar = GregorianCalendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", new Book());
        model.addAttribute("comment", new Comment());
        model.addAttribute("books", profileService.getBooks(profile, year));
        return PAGES_BOOKS;
    }

    @GetMapping("/books/{year}")
    public String getBooksToYear(@PathVariable Integer year, Model model, Principal principal) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", new Book());
        model.addAttribute("books", profileService.getBooks(profile, year));
        return PAGES_BOOKS;
    }

    @GetMapping("/books/plan")
    public String getPlanned(Model model, Principal principal) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        model.addAttribute("nav", "plan");
        model.addAttribute("book", new Book());
        model.addAttribute("books", profileService.getPlannedBooks(profile));
        return PAGES_BOOKS;
    }

    @GetMapping("/books/plan/search")
    public String getBooksByName(Model model,
                                 Principal principal,
                                 @RequestParam String search) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        model.addAttribute("nav", "plan");
        model.addAttribute("search", search);
        model.addAttribute("book", new Book());
        model.addAttribute("books", profileService.getPlannedBooksByName(profile, search));
        return PAGES_BOOKS;
    }

    @PostMapping("/update")
    public String update(Principal principal,
                         @RequestParam String name,
                         @RequestParam String surname) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        profileService.update(profile, name, surname);
        return REDIRECT_PROFILE;
    }

    @GetMapping("/books/notRead")
    public String getBooksNotRead(Principal principal, Model model) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.NOT_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", profileService.getNotReadBooks(profile));
        return PAGES_BOOKS;
    }

    @GetMapping("/books/wrongDates")
    public String getBooksWithWrongDate(Principal principal, Model model) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.WRONG_DATES.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", profileService.getWrongDatesBooks(profile));
        return PAGES_BOOKS;
    }

    @GetMapping("/books/lastRead")
    public String getBooksLastRead(Principal principal, Model model) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.LAST_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", profileService.getBooksLastRead(profile));
        return PAGES_BOOKS;
    }

    @GetMapping("/books/thisYearRead")
    public String getBooksThisYearRead(Principal principal, Model model) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.THIS_YEAR_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", profileService.getBooksThisYearRead(profile));
        return PAGES_BOOKS;
    }
}
