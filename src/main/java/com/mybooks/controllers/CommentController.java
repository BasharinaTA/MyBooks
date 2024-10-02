package com.mybooks.controllers;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Comment;
import com.mybooks.model.entities.Profile;
import com.mybooks.services.BookService;
import com.mybooks.services.CommentService;
import com.mybooks.services.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.mybooks.controllers.BookController.REDIRECT_BOOKS;

@Controller
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    public static final String PAGES_COMMENT = "pages/comment";
    private CommentService commentService;
    private BookService bookService;
    private ProfileService profileService;

    @GetMapping("/{bookId}")
    public String getComment(Principal principal, @PathVariable Integer bookId, Model model) {
        Profile profile = profileService.getByPrincipal(principal);
        Book book = bookService.getByIdAndProfile(bookId, profile);
        model.addAttribute("nav", "book");
        model.addAttribute("year", book.getDateStart().getYear() + 1900);
        model.addAttribute("book", book);
        model.addAttribute("comment", new Comment());
        return PAGES_COMMENT;
    }

    @GetMapping("/{bookId}/{commentId}")
    public String getComment(Principal principal, @PathVariable Integer bookId,
                             Model model, @PathVariable Integer commentId) {
        Profile profile = profileService.getByPrincipal(principal);
        Book book = bookService.getByIdAndProfile(bookId, profile);
        model.addAttribute("nav", "book");
        model.addAttribute("year", book.getDateStart().getYear() + 1900);
        model.addAttribute("book", book);
        model.addAttribute("comment", commentService.getByIdAndBook(commentId, book));
        return PAGES_COMMENT;
    }

    @PostMapping("/{bookId}")
    public String addComment(Principal principal, @PathVariable Integer bookId, Comment comment) {
        Profile profile = profileService.getByPrincipal(principal);
        Book book = bookService.getByIdAndProfile(bookId, profile);
        commentService.save(comment, book);
        int year = book.getDateStart().getYear() + 1900;
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/{bookId}/{commentId}")
    public String updateComment(Principal principal, @PathVariable Integer bookId,
                                @PathVariable Integer commentId, @RequestParam String text) {
        Profile profile = profileService.getByPrincipal(principal);
        Book book = bookService.getByIdAndProfile(bookId, profile);
        commentService.update(commentId, book, text);
        int year = book.getDateStart().getYear() + 1900;
        return REDIRECT_BOOKS + year;
    }
}
