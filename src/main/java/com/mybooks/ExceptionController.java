package com.mybooks;

import com.mybooks.exceptions.BaseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    public static final String PAGES_EXCEPTION = "pages/error500";

    @ExceptionHandler(BaseException.class)
    public String handleException() {
        return PAGES_EXCEPTION;
    }
}
