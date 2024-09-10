package com.mybooks.controllers;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Genre;
import com.mybooks.model.entities.Profile;
import com.mybooks.model.entities.Type;
import com.mybooks.services.BookService;
import com.mybooks.services.GenreService;
import com.mybooks.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;

@Controller
@AllArgsConstructor
@RequestMapping("/book")
public class BookController {

    public static final String REDIRECT_BOOKS = "redirect:/profile/books/";
    public static final String REDIRECT_BOOKS_PLAN = "redirect:/profile/books/plan";
    private BookService bookService;
    private ProfileService profileService;
    private GenreService genreService;

    @PostMapping("/{year}/form/add")
    public String addBook(Model model, @PathVariable Integer year) {
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        attributesFormAdd(model);
        return "pages/save";
    }

    private void attributesFormAdd(Model model) {
        Book book = new Book();
        book.setType(Type.BOOK.getDescription());
        book.setGenre(genreService.getAllByOrderByName().get(0));
        model.addAttribute("book", book);
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllByOrderByName());
    }

    @PostMapping("/{year}/form/edit/{id}")
    public String editBook(Model model, @PathVariable Integer year, @PathVariable Integer id) {
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllByOrderByName());
        return "pages/save";
    }

    @PostMapping("/{year}/save")
    public String addBookToYear(Book book,
                                @PathVariable Integer year,
                                Principal principal,
                                @RequestParam Integer genreId) {
        editProfile(book, principal, genreId);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/{year}/update/{id}")
    public String updateBook(Book book,
                             @PathVariable Integer year,
                             @PathVariable Integer id,
                             @RequestParam Integer genreId) {
        bookService.update(id, book, genreId);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/form/plan/add")
    public String addPlanBook(Model model) {
        model.addAttribute("nav", "plan");
        attributesFormAdd(model);
        return "pages/save";
    }

    @PostMapping("/plan/save")
    public String addPlanBook(Book book,
                              Principal principal,
                              @RequestParam Integer genreId) {
        editProfile(book, principal, genreId);
        return REDIRECT_BOOKS_PLAN;
    }

    @PostMapping("/form/plan/edit/{id}")
    public String editPlanBook(Model model, @PathVariable Integer id) {
        model.addAttribute("nav", "plan");
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllByOrderByName());
        return "pages/save";
    }

    @PostMapping("/plan/update/{id}")
    public String updatePlanBook(Book book,
                                 @RequestParam Integer genreId,
                                 @PathVariable Integer id) {
        bookService.update(id, book, genreId);
        return REDIRECT_BOOKS_PLAN;
    }

    private void editProfile(Book book, Principal principal, Integer id) {
        Profile profile = profileService.getProfileByPrincipal(principal);
        Genre genre = genreService.getById(id);
        book.setProfile(profile);
        book.setGenre(genre);
        bookService.save(book);
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
    public String updateDataFinish(@PathVariable Integer year, @PathVariable Integer id) {
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

    @PostMapping("/plan/delete/{id}")
    public String deletePlan(@PathVariable Integer id) {
        bookService.delete(id);
        return REDIRECT_BOOKS_PLAN;
    }
}
