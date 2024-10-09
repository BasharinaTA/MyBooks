package com.mybooks.controllers;

import com.mybooks.model.entities.Book;
import com.mybooks.model.entities.Comment;
import com.mybooks.services.BookService;
import com.mybooks.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.mybooks.controllers.BookController.REDIRECT_BOOKS;

@Controller
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    public static final String PAGES_COMMENT = "pages/comment";
    private CommentService commentService;
    private BookService bookService;

    @GetMapping("/{bookId}")
    public String getComment(@PathVariable Integer bookId, Model model) {
        Book book = bookService.getById(bookId);
        model.addAttribute("nav", "book");
        model.addAttribute("year", book.getDateStart().getYear());
        model.addAttribute("book", book);
        model.addAttribute("comment", new Comment());
        return PAGES_COMMENT;
    }

    @GetMapping("/{bookId}/{commentId}")
    public String getComment(@PathVariable Integer bookId, Model model, @PathVariable Integer commentId) {
        Book book = bookService.getById(bookId);
        model.addAttribute("nav", "book");
        model.addAttribute("year", book.getDateStart().getYear());
        model.addAttribute("book", book);
        model.addAttribute("comment", commentService.getByIdAndBook(commentId, book));
        return PAGES_COMMENT;
    }

    @PostMapping("/{bookId}")
    public String addComment(@PathVariable Integer bookId, Comment comment) {
        Book book = bookService.getById(bookId);
        commentService.save(comment, book);
        int year = book.getDateStart().getYear();
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/{bookId}/{commentId}")
    public String updateComment(@PathVariable Integer bookId, @PathVariable Integer commentId,
                                @RequestParam String text) {
        Book book = bookService.getById(bookId);
        commentService.update(commentId, book, text);
        int year = book.getDateStart().getYear();
        return REDIRECT_BOOKS + year;
    }
}
