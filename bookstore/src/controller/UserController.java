package controller;

import config.security.WebSecurityConfig;
import daoimpl.AccountDAOImpl;
import entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utility.UserForm;
import validator.UserFormValidator;

import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
@EnableWebMvc
public class UserController {
    @Autowired
    private AccountDAOImpl accountDAO;

    @Autowired
    private UserFormValidator userFormValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();

        if (target == null) {
            return;
        }

        if (target.getClass() == UserForm.class) {
            dataBinder.setValidator(userFormValidator);
        }
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login(HttpServletRequest request, ModelMap modelMap) {
        if (request.getUserPrincipal() != null) {
            return "redirect:/";
        }

        return "login";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String register(HttpServletRequest request, ModelMap modelMap) {
        if (request.getUserPrincipal() != null) {
            return "redirect:/";
        }

        UserForm userForm = new UserForm();
        modelMap.addAttribute("userForm", userForm);

        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String confirmRegistration(HttpServletRequest request, ModelMap modelMap,
                                      @ModelAttribute("userForm") @Validated UserForm userForm,
                                      BindingResult result,
                                      final RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            userForm.setValid(false);
            return "register";
        }

        userForm.setValid(true);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUserName(userForm.getUserName());
        accountEntity.seteMail(userForm.geteMail());
        accountEntity.setPhoneNumber(userForm.getPhoneNumber());
        accountEntity.setHomeAddress(userForm.getHomeAddress());
        accountEntity.setAdmin(false);
        accountEntity.setPasswordHash(WebSecurityConfig
                .passwordEncoder().encode(userForm.getPassword()));

        accountDAO.setSession();
        accountDAO.save(accountEntity);

        return "redirect:/login?registered=true";
    }
}
