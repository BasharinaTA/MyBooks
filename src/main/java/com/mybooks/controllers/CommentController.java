package com.mybooks.controllers;

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
@RequestMapping("/comment")
public class CommentController {

    public static final String PAGES_COMMENT = "pages/comment";
    private CommentService commentService;
    private BookService bookService;

    @GetMapping("/form/{year}/{bookId}")
    public String formComment(Model model, @PathVariable Integer year, @PathVariable Integer bookId) {
        model.addAttribute("nav", "book");
        model.addAttribute("year", year);
        model.addAttribute("book", bookService.getById(bookId));
        model.addAttribute("comment", new Comment());
        return PAGES_COMMENT;
    }

    @GetMapping("/form/{year}/{bookId}/{commentId}")
    public String formComment(Model model, @PathVariable Integer year, @PathVariable Integer bookId, @PathVariable Integer commentId) {
        model.addAttribute("nav", "book");
        model.addAttribute("book", bookService.getById(bookId));
        model.addAttribute("comment", commentService.getById(commentId));
        return PAGES_COMMENT;
    }

    @PostMapping("/{year}/save/{commentId}")
    public String addNewComment(@RequestParam String text, @PathVariable Integer year, @PathVariable Integer commentId) {
        commentService.save(text, commentId);
        return REDIRECT_BOOKS + year;
    }

    @PostMapping("/{year}/update/{commentId}")
    public String updateComment(@PathVariable Integer year, @PathVariable Integer commentId, @RequestParam String text) {
        Comment comment = commentService.getById(commentId);
        commentService.update(comment, text);
        return REDIRECT_BOOKS + year;
    }
}
