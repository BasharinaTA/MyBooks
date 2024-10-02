package com.mybooks.controllers;

import com.mybooks.model.dto.BookUpdateDto;
import com.mybooks.model.entities.*;
import com.mybooks.services.BookService;
import com.mybooks.services.GenreService;
import com.mybooks.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    public static final String PAGES_MAIN = "pages/main";
    public static final String REDIRECT_BOOKS = "redirect:/books/";
    public static final String REDIRECT_BOOKS_PLAN_SEARCH = "redirect:/books/plan/search";
    public static final String PAGES_BOOK = "pages/book";
    private BookService bookService;
    private ProfileService profileService;
    private GenreService genreService;

    @GetMapping
    public String getBooks(Principal principal, Model model) {
        Profile profile = profileService.getByPrincipal(principal);
        Integer year = GregorianCalendar.getInstance().get(Calendar.YEAR);
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", new Book());
//        model.addAttribute("comment", new Comment());
        model.addAttribute("books", bookService.getBooksBeingReadFilterByYear(profile, year));
        return PAGES_MAIN;
    }

    @GetMapping("/{year}")
    public String getBooks(Principal principal, Model model, @PathVariable Integer year) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", new Book());
        model.addAttribute("books", bookService.getBooksBeingReadFilterByYear(profile, year));
        return PAGES_MAIN;
    }

    @GetMapping("/plan")
    public String getPlannedBooks(Principal principal, Model model) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "plan");
        model.addAttribute("book", new Book());
        model.addAttribute("books", bookService.getPlannedBooksToRead(profile));
        return PAGES_MAIN;
    }

    @GetMapping("/plan/search")
    public String getPlannedBooks(Principal principal, Model model, @RequestParam(required = false) String search) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "plan");
        if (search != null) {
            model.addAttribute("search", search);
        }
        model.addAttribute("book", new Book());
        model.addAttribute("books",
                bookService.getPlannedBooksToReadByName(profile, (String) model.getAttribute("search")));
        return PAGES_MAIN;
    }

    @GetMapping("/book/{year}")
    public String getBook(Model model, @PathVariable Integer year) {
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        attributesForBook(model);
        return PAGES_BOOK;
    }

    private void attributesForBook(Model model) {
        Book book = new Book();
        book.setType(Type.BOOK.getDescription());
        book.setGenre(genreService.getAllOrderByName().get(0));
        model.addAttribute("book", book);
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllOrderByName());
    }

    @GetMapping("/book/{id}/{year}")
    public String getBook(Principal principal, Model model,
                          @PathVariable Integer year, @PathVariable Integer id) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", bookService.getByIdAndProfile(id, profile));
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllOrderByName());
        return PAGES_BOOK;
    }

    @PostMapping("/{year}")
    public String addBook(Principal principal, Book book,
                          @RequestParam Integer genreId, @PathVariable Integer year) {
        Profile profile = profileService.getByPrincipal(principal);
        bookService.save(book, genreId, profile);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/{id}/{year}")
    public String updateBook(Principal principal, @PathVariable Integer id, @RequestParam String type,
                             @RequestParam Integer genreId, @RequestParam String author,
                             @RequestParam String name,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFinish,
                             @PathVariable Integer year) {
        Profile profile = profileService.getByPrincipal(principal);
        BookUpdateDto bookData = new BookUpdateDto(id, type, genreId, author, name, dateStart, dateFinish);
        bookService.update(bookData, profile);
        return REDIRECT_BOOKS + year;
    }

    @GetMapping("/plan/book")
    public String getPlannedBook(Model model, @RequestParam String search) {
        model.addAttribute("nav", "plan");
        model.addAttribute("search", search);
        attributesForBook(model);
        return PAGES_BOOK;
    }

    @GetMapping("/plan/book/{id}")
    public String getPlannedBook(Principal principal, Model model,
                                 @RequestParam String search, @PathVariable Integer id) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "plan");
        model.addAttribute("search", search);
        model.addAttribute("book", bookService.getByIdAndProfile(id, profile));
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllOrderByName());
        return PAGES_BOOK;
    }

    @PostMapping("/plan")
    public String addPlannedBook(Principal principal, Book book, @RequestParam Integer genreId,
                                 RedirectAttributes ra, @RequestParam String search) {
        Profile profile = profileService.getByPrincipal(principal);
        bookService.save(book, genreId, profile);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @PostMapping("/plan/{id}")
    public String updatePlannedBook(Principal principal, @PathVariable Integer id,
                                    @RequestParam String type, @RequestParam Integer genreId,
                                    @RequestParam String author, @RequestParam String name,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFinish,
                                    RedirectAttributes ra, @RequestParam String search) {
        Profile profile = profileService.getByPrincipal(principal);
        BookUpdateDto bookData = new BookUpdateDto(id, type, genreId, author, name, dateStart, dateFinish);
        bookService.update(bookData, profile);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @PostMapping("/dateStart/{id}")
    public String updateDataStartBook(Principal principal, @PathVariable Integer id,
                                      RedirectAttributes ra, @RequestParam String search) {
        Profile profile = profileService.getByPrincipal(principal);
        bookService.updateDateStart(id, profile);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @PostMapping("/dateFinish/{id}/{year}")
    public String updateDataFinishBook(Principal principal, @PathVariable Integer id, @PathVariable Integer year) {
        Profile profile = profileService.getByPrincipal(principal);
        bookService.updateDateFinish(id, profile);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/delete/{id}/{year}")
    public String deleteBook(Principal principal, @PathVariable Integer id, @PathVariable Integer year) {
        Profile profile = profileService.getByPrincipal(principal);
        bookService.delete(id, profile);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/plan/delete/{id}")
    public String deleteBook(Principal principal, @PathVariable Integer id, RedirectAttributes ra, @RequestParam String search) {
        Profile profile = profileService.getByPrincipal(principal);
        bookService.delete(id, profile);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @GetMapping("/notRead")
    public String getBooksNotRead(Principal principal, Model model) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.NOT_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getBooksNotRead(profile));
        return PAGES_MAIN;
    }

    @GetMapping("/incorrectDates")
    public String getBooksWithIncorrectDates(Principal principal, Model model) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.INCORRECT_DATES.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getBooksWithIncorrectDates(profile));
        return PAGES_MAIN;
    }

    @GetMapping("/lastRead")
    public String getBooksLastRead(Principal principal, Model model) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.LAST_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getBooksLastRead(profile));
        return PAGES_MAIN;
    }

    @GetMapping("/thisYearRead")
    public String getBooksThisYearRead(Principal principal, Model model) {
        Profile profile = profileService.getByPrincipal(principal);
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.THIS_YEAR_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getBooksThisYearRead(profile));
        return PAGES_MAIN;
    }
}
