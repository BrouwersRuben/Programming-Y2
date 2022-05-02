package be.kdg.java2.project.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
@Profile("!test")
public class GeneralExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "General exception occurred...")
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception exception) throws Exception {
        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
            throw exception;
        }
        log.error("General exception: " + exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date().toString());
        mav.setViewName("error");
        return mav;
    }

    //If I use the response status here, it will redirect me to the general error page. The logger however shows that it is a database error...
    //@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Database exception occurred...")
    @ExceptionHandler(value = {DataAccessException.class})
    public ModelAndView databaseExceptionHandler(HttpServletRequest req, Exception exception) throws Exception {
        if (AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class) != null) {
            throw exception;
        }
        log.error("Database Exception: " + exception.getMessage());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("timestamp", new Date().toString());
        mav.setViewName("errorDB");
        return mav;
    }


}
