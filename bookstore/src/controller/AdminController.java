package controller;

import daoimpl.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utility.BookForm;
import utility.ItemForm;
import utility.OrderSelectorForm;
import validator.BookFormValidator;
import validator.ItemFormValidator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@Transactional
@EnableWebMvc
public class AdminController {
    @Autowired
    private BookDAOImpl bookDAO;
    @Autowired
    private GenreDAOImpl genreDAO;
    @Autowired
    private CoverTypeDAOImpl coverTypeDAO;
    @Autowired
    private PublisherDAOImpl publisherDAO;
    @Autowired
    private AuthorDAOImpl authorDAO;
    @Autowired
    private BookAuthorDAOImpl bookAuthorDAO;
    @Autowired
    private PurchaseDAOImpl purchaseDAO;
    @Autowired
    private OrderedBookDAOImpl orderedBookDAO;
    @Autowired
    private BookFormValidator bookFormValidator;
    @Autowired
    private ItemFormValidator itemFormValidator;
    @Autowired
    private ServletContext servletContext;

    private void setUpSelectors(ModelMap modelMap) {
        genreDAO.setSession();
        coverTypeDAO.setSession();
        publisherDAO.setSession();
        authorDAO.setSession();

        List<AuthorEntity> authors = authorDAO.getAll();
        modelMap.addAttribute("authors", authors);

        List<GenreEntity> genres = genreDAO.getAll();
        modelMap.addAttribute("genres", genres);

        List<CoverTypeEntity> covers = coverTypeDAO.getAll();
        modelMap.addAttribute("covers", covers);

        List<PublisherEntity> publishers = publisherDAO.getAll();
        modelMap.addAttribute("publishers", publishers);
    }

    private void saveImage(byte[] image, String name) {
        if (image == null || image.length == 0 || name == null) {
            return;
        }

        File file = new File(name);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(image);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getItemParameter(HttpServletRequest request) {
        String parameter = "";

        if (request.getParameter("author") != null) {
            parameter = "author";
        } else if (request.getParameter("publisher") != null) {
            parameter = "publisher";
        } else if (request.getParameter("coverType") != null) {
            parameter = "coverType";
        } else if (request.getParameter("genre") != null) {
            parameter = "genre";
        }

        return parameter;
    }

    private void saveBook(BookForm bookForm) {
        bookDAO.setSession();
        coverTypeDAO.setSession();
        genreDAO.setSession();
        publisherDAO.setSession();
        bookAuthorDAO.setSession();
        authorDAO.setSession();

        BookEntity bookEntity = (bookForm.getBookId() == null) ?
                new BookEntity() : bookDAO.getById(bookForm.getBookId());
        bookEntity.setBookName(bookForm.getBookName());
        bookEntity.setAvailableCount(bookForm.getAvailableCount());
        bookEntity.setCoverTypeId(coverTypeDAO.getById(bookForm.getCoverTypeId()));
        bookEntity.setGenreId(genreDAO.getById(bookForm.getGenreId()));
        bookEntity.setPublisherId(publisherDAO.getById(bookForm.getPublisherId()));
        bookEntity.setDescription(bookForm.getDescription());
        bookEntity.setPublicationDate(bookForm.getPublicationDate());
        bookEntity.setPageCount(bookForm.getPageCount());
        bookEntity.setBookPrice(bookForm.getBookPrice());

        if (!bookForm.getImage().isEmpty()) {
            bookEntity.setImageName(bookForm.getImage().getOriginalFilename());
            saveImage(bookForm.getImage().getBytes(),
                    servletContext.getRealPath("/resources/images") +
                            "/" + bookForm.getImage().getOriginalFilename());
        }

        bookDAO.save(bookEntity);
        bookForm.reduce();

        List<AuthorEntity> authorList;
        
        if (bookForm.getBookId() != null) {
            authorList = bookAuthorDAO.getAuthorsByBook(bookEntity);

            for (AuthorEntity author : authorList) {
                if (!bookForm.getBookAuthors().contains(author.getAuthorId())) {
                    bookAuthorDAO.delete(bookAuthorDAO.getByCompositeId(
                            bookForm.getBookId(), author.getAuthorId()));
                }
            }

            for (AuthorEntity author : authorList) {
                bookForm.getBookAuthors().removeIf(x -> x.equals(author.getAuthorId()));
            }
        }

        for (Long authorId : bookForm.getBookAuthors()) {
            BookAuthorEntity bookAuthorEntity = new BookAuthorEntity();
            bookAuthorEntity.setBookId(bookEntity);
            bookAuthorEntity.setAuthorId(authorDAO.getById(authorId));
            bookAuthorEntity.setId(new BookAuthorId(authorId, bookEntity.getBookId()));
            bookAuthorDAO.save(bookAuthorEntity);
        }
    }

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();

        if (target == null) {
            return;
        }

        if (target.getClass() == BookForm.class) {
            dataBinder.setValidator(bookFormValidator);
        } else if (target.getClass() == ItemForm.class) {
            dataBinder.setValidator(itemFormValidator);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = {"/addBook"}, method = RequestMethod.GET)
    public String addBook(ModelMap modelMap) {
        BookForm bookForm = new BookForm();
        modelMap.addAttribute("bookForm", bookForm);
        setUpSelectors(modelMap);

        return "addBook";
    }

    @RequestMapping(value = {"/addBook"}, method = RequestMethod.POST)
    public String confirmAddBook(ModelMap modelMap, HttpServletRequest request,
                                 @ModelAttribute("bookForm") @Validated BookForm bookForm,
                                 BindingResult result) {
        if (result.hasErrors()) {
            setUpSelectors(modelMap);
            return "addBook";
        }

        saveBook(bookForm);

        return "redirect:/?bookAdded=true";
    }

    @RequestMapping(value = {"/deleteBook/{id}"}, method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long id, ModelMap modelMap) {
        bookDAO.setSession();
        bookDAO.delete(bookDAO.getById(id));

        return "redirect:/?bookDeleted=true";
    }

    @RequestMapping(value = {"/addItem"}, method = RequestMethod.GET)
    public String addItem(ModelMap modelMap) {
        modelMap.addAttribute("itemForm", new ItemForm());

        return "addItem";
    }

    @RequestMapping(value = {"/addItem"}, method = RequestMethod.POST)
    public String confirmAddItem(ModelMap modelMap, HttpServletRequest request,
                             @ModelAttribute("itemForm") @Validated ItemForm itemForm,
                             BindingResult result) {
        String parameter = getItemParameter(request);

        if (result.hasErrors() || parameter.equals("")) {
            return "addItem";
        }

        switch (parameter) {
            case "author":
                authorDAO.setSession();
                AuthorEntity authorEntity = new AuthorEntity();
                authorEntity.setAuthorName(itemForm.getValue());
                authorDAO.save(authorEntity);
                break;
            case "publisher":
                publisherDAO.setSession();
                PublisherEntity publisherEntity = new PublisherEntity();
                publisherEntity.setPublisherName(itemForm.getValue());
                publisherDAO.save(publisherEntity);
                break;
            case "coverType":
                coverTypeDAO.setSession();
                CoverTypeEntity coverTypeEntity = new CoverTypeEntity();
                coverTypeEntity.setCoverTypeName(itemForm.getValue());
                coverTypeDAO.save(coverTypeEntity);
                break;
            case "genre":
                genreDAO.setSession();
                GenreEntity genreEntity = new GenreEntity();
                genreEntity.setGenreName(itemForm.getValue());
                genreDAO.save(genreEntity);
                break;
            default:
                break;
        }

        return "redirect:/?itemAdded=true";
    }

    @RequestMapping(value = {"/removeItem"}, method = RequestMethod.GET)
    public String removeItem(ModelMap modelMap) {
        setUpSelectors(modelMap);

        return "removeItem";
    }

    @RequestMapping(value = {"/removeItem/{type}/{id}"}, method = RequestMethod.GET)
    public String confirmRemoveItem(ModelMap modelMap, HttpServletRequest request,
                                    @PathVariable("id") Long id,
                                    @PathVariable("type") String type) {
        switch (type) {
            case "author":
                authorDAO.setSession();
                authorDAO.delete(authorDAO.getById(id));
                break;
            case "publisher":
                publisherDAO.setSession();
                publisherDAO.delete(publisherDAO.getById(id));
                break;
            case "coverType":
                coverTypeDAO.setSession();
                coverTypeDAO.delete(coverTypeDAO.getById(id));
                break;
            case "genre":
                genreDAO.setSession();
                genreDAO.delete(genreDAO.getById(id));
                break;
            default:
                break;
        }

        return "redirect:/removeItem?" + type + "&itemRemoved=true";
    }

    @RequestMapping(value = "/editBook", method = RequestMethod.GET)
    public String editBook(ModelMap modelMap,
                           @RequestParam(value = "id", defaultValue = "") Long id) {
        if (id == null) {
            return "redirect:/";
        }

        if (!modelMap.containsAttribute("bookForm")) {
            modelMap.addAttribute("bookForm", new BookForm());
        }

        setUpSelectors(modelMap);

        bookDAO.setSession();
        bookAuthorDAO.setSession();
        BookEntity editedBook = bookDAO.getById(id);

        if (editedBook == null) {
            return "redirect:/";
        }

        modelMap.addAttribute("editedBook", editedBook);
        modelMap.addAttribute("editedBookAuthors",
                bookAuthorDAO.getAuthorsByBook(editedBook));

        return "editBook";
    }

    @RequestMapping(value = "/editBook", method = RequestMethod.POST)
    public String confirmEditBook(ModelMap modelMap,
                                  @ModelAttribute("bookForm") @Validated BookForm bookForm,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.bookForm", bindingResult);
            redirectAttributes.addFlashAttribute("bookForm", bookForm);
            return "redirect:/editBook?id=" + bookForm.getBookId();
        }

        saveBook(bookForm);

        return "redirect:/?bookEdited=true";
    }

    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public String orderList(ModelMap modelMap) {
        purchaseDAO.setSession();
        modelMap.addAttribute("orders", purchaseDAO.getRelevant());
        modelMap.addAttribute("orderStatus", PurchaseEntity.OrderStatus.values());
        modelMap.addAttribute("orderSelector", new OrderSelectorForm());
        modelMap.addAttribute("statusStrings",
                new String[] {"В обработке", "Собран", "Доставлен", "Отменён"});

        return "orderList";
    }

    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public String orderListUpdate(ModelMap modelMap,
                                  @ModelAttribute("OrderSelectorForm") OrderSelectorForm orderSelectorForm,
                                  BindingResult result) {
        purchaseDAO.setSession();
        PurchaseEntity purchaseEntity = purchaseDAO.getById(orderSelectorForm.getOrderId());
        purchaseEntity.setOrderStatus(orderSelectorForm.getStatus());

        return "redirect:/orderList";
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String order(ModelMap modelMap,
                        @RequestParam(value = "id", defaultValue = "") Long id) {
        purchaseDAO.setSession();
        orderedBookDAO.setSession();
        modelMap.addAttribute("orderData", purchaseDAO.getById(id));
        modelMap.addAttribute("orderedBooks", orderedBookDAO.getByOrderId(id));
        modelMap.addAttribute("customerAccount", purchaseDAO.getById(id).getUserId());
        modelMap.addAttribute("orderStatus", PurchaseEntity.OrderStatus.values());
        modelMap.addAttribute("orderSelector", new OrderSelectorForm());
        modelMap.addAttribute("statusStrings",
                new String[] {"В обработке", "Собран", "Доставлен", "Отменён"});

        return "order";
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String orderUpdate(ModelMap modelMap,
                              @ModelAttribute("OrderSelectorForm") OrderSelectorForm orderSelectorForm,
                              BindingResult result) {
        purchaseDAO.setSession();
        PurchaseEntity purchaseEntity = purchaseDAO.getById(orderSelectorForm.getOrderId());
        purchaseEntity.setOrderStatus(orderSelectorForm.getStatus());

        return "redirect:/order?id=" + orderSelectorForm.getOrderId();
    }
}
