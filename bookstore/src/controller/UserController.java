package controller;

import config.security.WebSecurityConfig;
import daoimpl.AccountDAOImpl;
import daoimpl.BookDAOImpl;
import daoimpl.OrderedBookDAOImpl;
import daoimpl.PurchaseDAOImpl;
import entity.AccountEntity;
import entity.OrderedBookEntity;
import entity.OrderedBookId;
import entity.PurchaseEntity;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utility.*;
import validator.CartInfoValidator;
import validator.OrderFormValidator;
import validator.UserFormValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
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
    @Autowired
    private OrderFormValidator orderFormValidator;

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
        } else if (target.getClass() == OrderForm.class) {
            dataBinder.setValidator(orderFormValidator);
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
    public String confirmRegistration(@ModelAttribute("userForm") @Validated UserForm userForm,
                                      BindingResult result) {
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
            final String[] orderStatusStrings = {"В обработке", "Собран", "Отменён", "Доставлен"};

            order.setStatus(orderStatusStrings[purchase.getOrderStatus().ordinal()]);
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
    public String confirmUserEdit(@ModelAttribute("userAccountForm") @Validated UserForm userForm,
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
    public String deleteAccount(HttpServletRequest request) {
        accountDAO.setSession();

        accountDAO.delete(accountDAO.getByEMail(request.getUserPrincipal().getName()));
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/?accountDeleted=true";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String cart(ModelMap modelMap, HttpServletRequest request) {
        modelMap.addAttribute("cartForm", getSessionCart(request));

        return "cart";
    }

    @RequestMapping(value = {"/cancelOrder/{id}"}, method = RequestMethod.GET)
    public String cancelOrder(@PathVariable("id") Long id) {
        purchaseDAO.setSession();
        orderedBookDAO.setSession();

        purchaseDAO.getById(id).setOrderStatus(PurchaseEntity.OrderStatus.CANCELED);

        for (OrderedBookEntity orderedBook : orderedBookDAO.getByOrderId(id)) {
            orderedBook.getBookId().setAvailableCount(
                    orderedBook.getBookId().getAvailableCount() + orderedBook.getBookCount());
        }

        return "redirect:/account#ordersInfo";
    }

    @RequestMapping(value = {"/addToCart/{id}"}, method = RequestMethod.GET)
    public String addToCart(@PathVariable("id") Long id, HttpServletRequest request) {
        CartInfo cartInfo = getSessionCart(request);

        bookDAO.setSession();

        if (bookDAO.getById(id).getAvailableCount() > 0 &&
                (cartInfo.getItem(id) == null ||
                        bookDAO.getById(id).getAvailableCount() >=
                        cartInfo.getItem(id).getQuantity() + 1)) {
            cartInfo.addItem(bookDAO.getById(id));
            return "redirect:/book?id=" + id + "&addedToCart=true";
        } else {
            return "redirect:/book?id=" + id + "&addedToCart=false";
        }
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.POST)
    public String cartUpdateQuantity(HttpServletRequest request,
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
    public String deleteFromCart(@PathVariable("id") Long id, HttpServletRequest request) {
        CartInfo cartInfo = getSessionCart(request);

        cartInfo.removeItem(id);

        return "redirect:/cart";
    }

    @RequestMapping(value = {"/clearCart"}, method = RequestMethod.GET)
    public String clearCart(HttpServletRequest request) {
        getSessionCart(request).clearCart();

        return "redirect:/cart";
    }

    @RequestMapping(value = {"/placeOrder"}, method = RequestMethod.GET)
    public String placeOrder(ModelMap modelMap, HttpServletRequest request) {
        if (getSessionCart(request).getBookList().isEmpty() &&
                request.getParameter("orderNumber") == null) {
            return "redirect:/cart?isEmpty=true";
        }

        accountDAO.setSession();
        modelMap.addAttribute("orderForm", new OrderForm());
        modelMap.addAttribute("orderingUser",
                accountDAO.getByEMail(request.getUserPrincipal().getName()));

        return "placeOrder";
    }

    @RequestMapping(value = {"/placeOrder"}, method = RequestMethod.POST)
    public String confirmOrder(HttpServletRequest request,
                               @ModelAttribute("orderForm") @Validated OrderForm orderForm,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "placeOrder";
        }

        accountDAO.setSession();
        purchaseDAO.setSession();
        orderedBookDAO.setSession();

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        CartInfo cartInfo = getSessionCart(request);

        purchaseEntity.setOrderStatus(PurchaseEntity.OrderStatus.IN_PROCESSING);
        purchaseEntity.setUserId(accountDAO.getByEMail(request.getUserPrincipal().getName()));
        purchaseEntity.setTotalPrice(cartInfo.getTotalPrice());
        purchaseEntity.setDeliveryAddress(orderForm.getDeliveryAddress());
        purchaseEntity.setDeliveryDate(Timestamp.valueOf(orderForm.getDeliveryDate() +
                " 00:00:00"));
        purchaseEntity.setOrderDate(new Timestamp(System.currentTimeMillis()));

        purchaseDAO.save(purchaseEntity);

        for (CartItem item : cartInfo.getBookList()) {
            OrderedBookEntity orderedBookEntity = new OrderedBookEntity();
            orderedBookEntity.setBookId(item.getBook());
            orderedBookEntity.setBookCount(item.getQuantity());
            orderedBookEntity.setOrder(purchaseEntity);
            orderedBookEntity.setId(new OrderedBookId(purchaseEntity.getOrderId(),
                    item.getBook().getBookId()));
            orderedBookDAO.save(orderedBookEntity);
            item.getBook().setAvailableCount(item.getBook().getAvailableCount() - item.getQuantity());
        }

        cartInfo.clearCart();

        return "redirect:/placeOrder?success=true&orderNumber=" + purchaseEntity.getOrderId();
    }
}
