package com.mybooks.controllers;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Profile;
import com.mybooks.model.entities.Type;
import com.mybooks.services.BookService;
import com.mybooks.services.ProfileServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Date;

@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    public static final String REDIRECT_BOOKS = "redirect:/profile/books/";
    public static final String REDIRECT_BOOKS_PLAN = "redirect:/profile/books/plan";
    public static final String REDIRECT_PROFILE_BOOKS_NOT_READ = "redirect:/profile/books/notRead";
    private BookService bookService;
    private ProfileServiceImpl profileService;

    @PostMapping("/form/add")
    public String addBook(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("types", Type.values());
        return "pages/save";
    }

    @PostMapping("/form/plan/add")
    public String addPlanBook(Model model) {
//        model.addAttribute("name", name);
        model.addAttribute("book", new Book());
        model.addAttribute("types", Type.values());
        return "pages/savePlan";
    }

    @PostMapping("/form/edit/{id}")
    public String editBook(Model model, @PathVariable Integer id) {
        model.addAttribute("types", Type.values());
        model.addAttribute("book", bookService.getById(id));
        return "pages/save";
    }

    @PostMapping("/form/plan/edit/{id}")
    public String editPlanBook(Model model, @PathVariable Integer id) {
        model.addAttribute("types", Type.values());
        model.addAttribute("book", bookService.getById(id));
        return "pages/savePlan";
    }

    @PostMapping("/save")
    public String addBook(Book book, Principal principal) {
        editProfile(book, principal);
        return REDIRECT_BOOKS;
    }


    @PostMapping("/save/{year}")
    public String addBookToYear(Book book, @PathVariable Integer year, Principal principal) {
        editProfile(book, principal);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/plan/save")
    public String addPlanBook(Book book, Principal principal) {
        editProfile(book, principal);
        return REDIRECT_BOOKS_PLAN;
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Integer id, Book book) {
        bookService.update(id, book);
        return REDIRECT_BOOKS;
    }

    @PostMapping("/update/plan/{id}")
    public String updatePlanBook(@PathVariable Integer id, Book book) {
        bookService.update(id, book);
        return REDIRECT_BOOKS_PLAN;
    }

    private void editProfile(Book book, Principal principal) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        book.setProfile(profile);
        bookService.save(book);
    }

    @PostMapping("/update/{year}/{id}")
    public String update(@PathVariable Integer year, @PathVariable Integer id, Book book) {
        bookService.update(id, book);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/updateDateStart/{id}")
    public String updateDataStart(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        book.setDateStart(new Date());
        book.setDateFinish(null);
        bookService.save(book);
        return REDIRECT_BOOKS_PLAN;
    }

    @PostMapping("/updateDateFinish/{year}/{id}")
    public String updateDataFinish(@PathVariable Integer year, @PathVariable Integer id) throws Exception {
        Book book = bookService.getById(id);
        book.setDateFinish(new Date());
        bookService.save(book);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/delete/{year}/{id}")
    public String delete(@PathVariable Integer year, @PathVariable Integer id) {
        bookService.delete(id);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        bookService.delete(id);
        return REDIRECT_PROFILE_BOOKS_NOT_READ;
    }

    @PostMapping("/plan/delete/{id}")
    public String deletePlan(@PathVariable Integer id) {
        bookService.delete(id);
        return REDIRECT_BOOKS_PLAN;
    }
}
