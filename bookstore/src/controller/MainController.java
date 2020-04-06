package controller;

import daoimpl.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import utility.BookFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Transactional
@EnableWebMvc
public class MainController {
    @Autowired
    private BookDAOImpl bookDAO;
    @Autowired
    private BookAuthorDAOImpl bookAuthorDAO;
    @Autowired
    private GenreDAOImpl genreDAO;
    @Autowired
    private CoverTypeDAOImpl coverTypeDAO;
    @Autowired
    private PublisherDAOImpl publisherDAO;

    private void setUpSelectors(ModelMap modelMap) {
        genreDAO.setSession();
        coverTypeDAO.setSession();
        publisherDAO.setSession();

        List<GenreEntity> genres = genreDAO.getAll();
        modelMap.addAttribute("genres", genres);

        List<CoverTypeEntity> covers = coverTypeDAO.getAll();
        modelMap.addAttribute("covers", covers);

        List<PublisherEntity> publishers = publisherDAO.getAll();
        modelMap.addAttribute("publishers", publishers);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(ModelMap modelMap) {
        bookDAO.setSession();
        bookAuthorDAO.setSession();

        List<BookEntity> books = bookDAO.getAll();
        Map<BookEntity, List<AuthorEntity>> result = new HashMap<>();

        for (BookEntity book : books) {
            result.put(book, bookAuthorDAO.getAuthorsByBook(book));
        }

        modelMap.addAttribute("bookList", result);

        BookFilter filter = new BookFilter();
        modelMap.addAttribute("bookFilterForm", filter);

        setUpSelectors(modelMap);

        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String applyBookFilter(ModelMap modelMap,
                                  @ModelAttribute("bookFilterForm") BookFilter filter) {
        bookDAO.setSession();
        bookAuthorDAO.setSession();

        List<BookEntity> books;
        books = bookDAO.getByParameters(filter.getName(),
                filter.getMinDate(), filter.getMaxDate(),
                filter.getMinPages(), filter.getMaxPages(),
                filter.getMinPrice(), filter.getMaxPrice(),
                filter.getAvailability(), filter.getPublisher(),
                filter.getGenre(), filter.getCover());

        Map<BookEntity, List<AuthorEntity>> result = new HashMap<>();

        for (BookEntity book : books) {
            result.put(book, bookAuthorDAO.getAuthorsByBook(book));
        }

        modelMap.addAttribute("bookList", result);

        setUpSelectors(modelMap);

        return "index";
    }
}
