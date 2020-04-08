package controller;

import daoimpl.AccountDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@Transactional
@EnableWebMvc
public class UserController {
    @Autowired
    private AccountDAOImpl accountDAO;

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(ModelMap modelMap) {
        return "login";
    }
}
