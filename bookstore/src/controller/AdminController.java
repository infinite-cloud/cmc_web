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
import utility.BookForm;
import validator.BookFormValidator;

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
    private BookFormValidator bookFormValidator;
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

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();

        if (target == null) {
            return;
        }

        if (target.getClass() == BookForm.class) {
            dataBinder.setValidator(bookFormValidator);
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

        bookDAO.setSession();
        coverTypeDAO.setSession();
        genreDAO.setSession();
        publisherDAO.setSession();
        bookAuthorDAO.setSession();
        authorDAO.setSession();

        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName(bookForm.getBookName());
        bookEntity.setAvailableCount(bookForm.getAvailableCount());
        bookEntity.setCoverTypeId(coverTypeDAO.getById(bookForm.getCoverTypeId()));
        bookEntity.setGenreId(genreDAO.getById(bookForm.getGenreId()));
        bookEntity.setPublisherId(publisherDAO.getById(bookForm.getPublisherId()));
        bookEntity.setDescription(bookForm.getDescription());
        bookEntity.setPublicationDate(bookForm.getPublicationDate());
        bookEntity.setPageCount(bookForm.getPageCount());
        bookEntity.setImageName(bookForm.getImage().getOriginalFilename());
        bookEntity.setBookPrice(bookForm.getBookPrice());
        bookDAO.save(bookEntity);

        saveImage(bookForm.getImage().getBytes(),servletContext.getRealPath("/resources/images") +
                "/" + bookForm.getImage().getOriginalFilename());
        bookForm.reduce();

        for (Long authorId : bookForm.getBookAuthors()) {
            BookAuthorEntity bookAuthorEntity = new BookAuthorEntity();
            bookAuthorEntity.setBookId(bookEntity);
            bookAuthorEntity.setAuthorId(authorDAO.getById(authorId));
            bookAuthorEntity.setId(new BookAuthorId(authorId, bookEntity.getBookId()));
            bookAuthorDAO.save(bookAuthorEntity);
        }

        return "redirect:/?bookAdded=true";
    }

    @RequestMapping(value = {"/deleteBook/{id}"}, method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long id, ModelMap modelMap) {
        bookDAO.setSession();
        bookDAO.delete(bookDAO.getById(id));

        return "redirect:/?bookDeleted=true";
    }
}
