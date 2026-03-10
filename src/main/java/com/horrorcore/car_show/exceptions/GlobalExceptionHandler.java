package com.horrorcore.car_show.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@ControllerAdvice //<- This helps handle side effects that you can make in
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCarIdException.class)
    public ModelAndView handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView error = new ModelAndView("error");
        error.addObject("errorMessage", e.getMessage());
        error.addObject("urlPath", request.getRequestURI());
        error.addObject("timestamp", LocalDateTime.now());
        return error;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView catchAll(Exception e, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView error = new ModelAndView("error");
        error.addObject("errorMessage", e.getMessage());
        error.addObject("urlPath", request.getRequestURI());
        error.addObject("timestamp", LocalDateTime.now());
        return error;
    }
}
