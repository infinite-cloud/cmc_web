package controller;

import config.security.WebSecurityConfig;
import daoimpl.AccountDAOImpl;
import daoimpl.BookDAOImpl;
import daoimpl.OrderedBookDAOImpl;
import daoimpl.PurchaseDAOImpl;
import entity.AccountEntity;
import entity.OrderedBookEntity;
import entity.PurchaseEntity;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utility.CartInfo;
import utility.UserForm;
import utility.UserOrder;
import validator.CartInfoValidator;
import validator.UserFormValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
@EnableWebMvc
public class UserController {
    @Autowired
    private AccountDAOImpl accountDAO;

    @Autowired
    private OrderedBookDAOImpl orderedBookDAO;

    @Autowired
    private PurchaseDAOImpl purchaseDAO;

    @Autowired
    private BookDAOImpl bookDAO;

    @Autowired
    private UserFormValidator userFormValidator;

    @Autowired
    private CartInfoValidator cartInfoValidator;

    private CartInfo getSessionCart(HttpServletRequest request) {
        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("cartInfo");

        if (cartInfo == null) {
            cartInfo = new CartInfo();
            request.getSession().setAttribute("cartInfo", cartInfo);
        }

        return cartInfo;
    }

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();

        if (target == null) {
            return;
        }

        if (target.getClass() == UserForm.class) {
            dataBinder.setValidator(userFormValidator);
        } else if (target.getClass() == CartInfo.class) {
            dataBinder.setValidator(cartInfoValidator);
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
            return "register";
        }

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

    @RequestMapping(value = {"/account"}, method = RequestMethod.GET)
    public String account(HttpServletRequest request, ModelMap modelMap) {
        accountDAO.setSession();
        orderedBookDAO.setSession();
        purchaseDAO.setSession();
        bookDAO.setSession();

        AccountEntity accountEntity = accountDAO.getByEMail(request.getUserPrincipal().getName());
        modelMap.addAttribute("user", accountEntity);

        if (!modelMap.containsAttribute("userAccountForm")) {
            UserForm userForm = new UserForm();
            modelMap.addAttribute("userAccountForm", userForm);
        }

        List<UserOrder> userOrders = new ArrayList<>();

        for (PurchaseEntity purchase : purchaseDAO.getByUserId(accountEntity.getUserId())) {
            UserOrder order = new UserOrder();

            order.setId(purchase.getOrderId());
            order.setPrice(purchase.getTotalPrice());

            switch (purchase.getOrderStatus()) {
                case IN_PROCESSING:
                    order.setStatus("В обработке");
                    break;
                case READY:
                    order.setStatus("Собран");
                    break;
                case CANCELED:
                    order.setStatus("Отменён");
                    break;
                case DELIVERED:
                    order.setStatus("Доставлен");
                    break;
                default:
                    order.setStatus("");
                    break;
            }

            order.setOrderDate(purchase.getOrderDate().toString().substring(0, 10));
            order.setDeliveryDate(purchase.getDeliveryDate().toString().substring(0, 10));
            order.setDeliveryAddress(purchase.getDeliveryAddress());

            for (OrderedBookEntity orderedBook : orderedBookDAO.getByOrderId(order.getId())) {
                Pair<String, Integer> book = new Pair<>(
                        bookDAO.getById(orderedBook.getBookId().getBookId()).getBookName(),
                        orderedBook.getBookCount());
                order.getBooks().add(book);
            }

            userOrders.add(order);
        }

        modelMap.addAttribute("userOrders", userOrders);

        return "account";
    }

    @RequestMapping(value = {"/account"}, method = RequestMethod.POST)
    public String confirmUserEdit(HttpServletRequest request, ModelMap modelMap,
                                      @ModelAttribute("userAccountForm") @Validated UserForm userForm,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.userAccountForm", result);
            redirectAttributes.addFlashAttribute("userAccountForm", userForm);
            return "redirect:/account?edit=true";
        }

        accountDAO.setSession();

        AccountEntity accountEntity = accountDAO.getByEMail(userForm.geteMail());
        accountEntity.setUserName(userForm.getUserName());
        accountEntity.setPhoneNumber(userForm.getPhoneNumber());
        accountEntity.setHomeAddress(userForm.getHomeAddress());

        if (!userForm.getPassword().equals("")) {
            accountEntity.setPasswordHash(WebSecurityConfig
                    .passwordEncoder().encode(userForm.getPassword()));
        }

        accountDAO.save(accountEntity);

        return "redirect:/account";
    }

    @RequestMapping(value = {"/deleteAccount"}, method = RequestMethod.GET)
    public String deleteAccount(ModelMap modelMap, HttpServletRequest request) {
        accountDAO.setSession();

        accountDAO.delete(accountDAO.getByEMail(request.getUserPrincipal().getName()));

        return "redirect:/logout";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String cart(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("cartForm", getSessionCart(request));

        return "cart";
    }

    @RequestMapping(value = {"/cancelOrder/{id}"}, method = RequestMethod.GET)
    public String cancelOrder(@PathVariable("id") Long id, ModelMap modelMap) {
        purchaseDAO.setSession();
        purchaseDAO.getById(id).setOrderStatus(PurchaseEntity.OrderStatus.CANCELED);

        return "redirect:/account#ordersInfo";
    }

    @RequestMapping(value = {"/addToCart/{id}"}, method = RequestMethod.GET)
    public String addToCart(@PathVariable("id") Long id, ModelMap modelMap,
                            HttpServletRequest request) {
        CartInfo cartInfo = getSessionCart(request);

        bookDAO.setSession();
        cartInfo.addItem(bookDAO.getById(id));

        return "redirect:/book?id=" + id + "&addedToCart=true";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.POST)
    public String cartUpdateQuantity(HttpServletRequest request, ModelMap modelMap,
                                     @ModelAttribute("cartForm") @Validated CartInfo cartForm,
                                     BindingResult result) {
        if (result.hasErrors()) {
            return "cart";
        }

        CartInfo cartInfo = getSessionCart(request);
        cartInfo.updateQuantity(cartForm);

        return "redirect:/cart";
    }

    @RequestMapping(value = {"/deleteFromCart/{id}"}, method = RequestMethod.GET)
    public String deleteFromCart(@PathVariable("id") Long id, ModelMap modelMap,
                                 HttpServletRequest request) {
        CartInfo cartInfo = getSessionCart(request);

        cartInfo.removeItem(id);

        return "redirect:/cart";
    }

    @RequestMapping(value = {"/clearCart"}, method = RequestMethod.GET)
    public String clearCart(ModelMap modelMap, HttpServletRequest request) {
        getSessionCart(request).clearCart();

        return "redirect:/cart";
    }
}
