package be.kdg.java2.project.presentation.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping
    public String indexPage(Model model) {
        return "index";
    }
}
