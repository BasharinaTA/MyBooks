package com.mybooks.controllers;

import com.mybooks.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.mybooks.controllers.BookController.REDIRECT_BOOKS;

@Controller
@AllArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @PostMapping("/save/{year}")
    public String addComment(@PathVariable Integer year,
                             @RequestParam String text) {
        commentService.save(text);
        return REDIRECT_BOOKS + year;
    }
}
