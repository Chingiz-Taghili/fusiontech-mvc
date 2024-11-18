package com.multishop.fusiontech.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    //ADMIN rolu olmayanı "error"-a yönləndirmə
    @ExceptionHandler(AdminAccessException.class)
    public ModelAndView handleAdminAccessException() {
        return new ModelAndView("error");
    }
}
