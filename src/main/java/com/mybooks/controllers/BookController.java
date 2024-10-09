package com.mybooks.controllers;

import com.mybooks.model.dto.BookUpdateDto;
import com.mybooks.model.entities.*;
import com.mybooks.services.BookService;
import com.mybooks.services.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/books")
public class BookController {

    public static final String PAGES_MAIN = "pages/main";
    public static final String REDIRECT_BOOKS = "redirect:/books/";
    public static final String REDIRECT_BOOKS_PLAN_SEARCH = "redirect:/books/plan/search";
    public static final String PAGES_BOOK = "pages/book";
    private BookService bookService;
    private GenreService genreService;

    @GetMapping
    public String getBooks(Model model) {
        Integer year = LocalDate.now().getYear();
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", new Book());
        model.addAttribute("books", bookService.getReadingBooksByYear(year));
        return PAGES_MAIN;
    }

    @GetMapping("/{year}")
    public String getBooks(Model model, @PathVariable Integer year) {
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", new Book());
        model.addAttribute("books", bookService.getReadingBooksByYear(year));
        return PAGES_MAIN;
    }

    @GetMapping("/plan")
    public String getPlannedBooks(Model model) {
        model.addAttribute("nav", "plan");
        model.addAttribute("book", new Book());
        model.addAttribute("books", bookService.getPlannedBooks());
        return PAGES_MAIN;
    }

    @GetMapping("/plan/search")
    public String getPlannedBooks(Model model, @RequestParam(required = false) String search) {
     model.addAttribute("nav", "plan");
        if (search != null) {
            model.addAttribute("search", search);
        }
        model.addAttribute("book", new Book());
        model.addAttribute("books",
                bookService.getPlannedBooksByName((String) model.getAttribute("search")));
        return PAGES_MAIN;
    }

    @GetMapping("/book/{year}")
    public String getBook(Model model, @PathVariable Integer year) {
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        writeAttributesForBook(model);
        return PAGES_BOOK;
    }

    private void writeAttributesForBook(Model model) {
        Book book = new Book();
        book.setType(Type.BOOK.getDescription());
        List<Genre> genres = genreService.getAllByOrderByName();
        book.setGenre(genres.get(0));
        model.addAttribute("book", book);
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genres);
    }

    @GetMapping("/book/{id}/{year}")
    public String getBook(Model model, @PathVariable Integer year, @PathVariable Integer id) {
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllByOrderByName());
        return PAGES_BOOK;
    }

    @PostMapping("/{year}")
    public String addBook(Book book, @RequestParam Integer genreId, @PathVariable Integer year) {
        bookService.save(book, genreId);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/{id}/{year}")
    public String updateBook(@PathVariable Integer id, @RequestParam String type, @RequestParam Integer genreId,
                             @RequestParam String author, @RequestParam String name,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateStart,
                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFinish,
                             @PathVariable Integer year) {
        BookUpdateDto bookData = new BookUpdateDto(id, type, genreId, author, name, dateStart, dateFinish);
        bookService.update(bookData);
        return REDIRECT_BOOKS + year;
    }

    @GetMapping("/plan/book")
    public String getPlannedBook(Model model, @RequestParam String search) {
        model.addAttribute("nav", "plan");
        model.addAttribute("search", search);
        writeAttributesForBook(model);
        return PAGES_BOOK;
    }

    @GetMapping("/plan/book/{id}")
    public String getPlannedBook(Model model, @RequestParam String search, @PathVariable Integer id) {
        model.addAttribute("nav", "plan");
        model.addAttribute("search", search);
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("types", Type.values());
        model.addAttribute("genres", genreService.getAllByOrderByName());
        return PAGES_BOOK;
    }

    @PostMapping("/plan")
    public String addPlannedBook(Book book, @RequestParam Integer genreId,
                                 RedirectAttributes ra, @RequestParam String search) {
        bookService.save(book, genreId);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @PostMapping("/plan/{id}")
    public String updatePlannedBook(@PathVariable Integer id, @RequestParam String type, @RequestParam Integer genreId,
                                    @RequestParam String author, @RequestParam String name,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateStart,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFinish,
                                    RedirectAttributes ra, @RequestParam String search) {
        BookUpdateDto bookData = new BookUpdateDto(id, type, genreId, author, name, dateStart, dateFinish);
        bookService.update(bookData);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @PostMapping("/dateStart/{id}")
    public String updateDataStartBook(@PathVariable Integer id, RedirectAttributes ra, @RequestParam String search) {
        bookService.updateDateStart(id);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @PostMapping("/dateFinish/{id}/{year}")
    public String updateDataFinishBook(@PathVariable Integer id, @PathVariable Integer year) {
        bookService.updateDateFinish(id);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/delete/{id}/{year}")
    public String deleteBook(@PathVariable Integer id, @PathVariable Integer year) {
        bookService.delete(id);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/plan/delete/{id}")
    public String deleteBook(@PathVariable Integer id, RedirectAttributes ra, @RequestParam String search) {
        bookService.delete(id);
        ra.addFlashAttribute("search", search);
        return REDIRECT_BOOKS_PLAN_SEARCH;
    }

    @GetMapping("/notRead")
    public String getBooksNotRead(Model model) {
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.NOT_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getNotReadBooks());
        return PAGES_MAIN;
    }

    @GetMapping("/incorrectDates")
    public String getBooksWithIncorrectDates(Model model) {
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.INCORRECT_DATES.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getBooksWithIncorrectDates());
        return PAGES_MAIN;
    }

    @GetMapping("/lastRead")
    public String getBooksLastRead(Model model) {
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.LAST_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getBooksLastRead());
        return PAGES_MAIN;
    }

    @GetMapping("/thisYearRead")
    public String getBooksThisYearRead(Model model) {
        model.addAttribute("nav", "info");
        model.addAttribute("name", Report.THIS_YEAR_READ.getName());
        model.addAttribute("reports", Report.values());
        model.addAttribute("books", bookService.getBooksThisYearRead());
        return PAGES_MAIN;
    }
}
