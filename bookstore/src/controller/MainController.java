package controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@Transactional
@EnableWebMvc
public class MainController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }
}
