package be.kdg.java2.project.presentation.mvc;

import be.kdg.java2.project.presentation.api.dto.UserDTO;
import be.kdg.java2.project.presentation.mvc.viewmodels.LoginViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/login")
    public ModelAndView login(ModelAndView mav){
        mav.addObject("user", new LoginViewModel());
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/registration")
    public ModelAndView registration(ModelAndView mav) {
        mav.addObject("user", new UserDTO());
        mav.setViewName("register");
        return mav;
    }
}
