package com.mybooks;

import com.mybooks.exceptions.BaseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    public static final String PAGES_BASE_EXCEPTION = "pages/base-exception";

    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException exception, Model model) {
        model.addAttribute("exception", exception.getMessage());
        return PAGES_BASE_EXCEPTION;
    }
}
