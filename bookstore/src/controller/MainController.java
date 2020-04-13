package controller;

import daoimpl.*;
import entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import utility.BookFilter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private AuthorDAOImpl authorDAO;

    @InitBinder
    public void myInitBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

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
                                  @ModelAttribute("bookFilterForm") BookFilter filter,
                                  BindingResult bindingResult) {
        bookDAO.setSession();
        bookAuthorDAO.setSession();
        authorDAO.setSession();

        List<BookEntity> books;
        books = bookDAO.getByParameters(filter.getName(),
                filter.getMinDate(), filter.getMaxDate(),
                filter.getMinPages(), filter.getMaxPages(),
                filter.getMinPrice(), filter.getMaxPrice(),
                filter.getAvailability(), filter.getPublisher(),
                filter.getGenre(), filter.getCover());

        List<AuthorEntity> authors = new ArrayList<>();
        boolean isFilterEmpty = true;

        for (Map.Entry<Integer, String> author : filter.getAuthors().entrySet()) {
            if (author == null || author.getValue().equals("")) {
                continue;
            }

            isFilterEmpty = false;
            authors = Stream.concat(authors.stream(), authorDAO.getByName(author.getValue()).stream())
                    .collect(Collectors.toList());
        }

        if (authors.isEmpty() && !isFilterEmpty) {
            books.clear();
        } else if (!isFilterEmpty) {
            List<BookEntity> booksToRemove = new ArrayList<>();

            for (BookEntity book : books) {
                if (Collections.disjoint(bookAuthorDAO.getAuthorsByBook(book), authors)) {
                    booksToRemove.add(book);
                }
            }

            books.removeAll(booksToRemove);
        }

        Map<BookEntity, List<AuthorEntity>> result = new HashMap<>();

        for (BookEntity book : books) {
            result.put(book, bookAuthorDAO.getAuthorsByBook(book));
        }

        modelMap.addAttribute("bookList", result);

        setUpSelectors(modelMap);

        return "index";
    }

    @RequestMapping(value = {"/book"}, method = RequestMethod.GET)
    public String book(ModelMap modelMap,
                       @RequestParam(value = "id", defaultValue = "") Long id) {
        bookDAO.setSession();
        bookAuthorDAO.setSession();

        BookEntity bookEntity;

        if (id == null || (bookEntity = bookDAO.getById(id)) == null) {
            return "redirect:/";
        }

        modelMap.addAttribute("bookEntity", bookEntity);
        modelMap.addAttribute("bookAuthors", bookAuthorDAO.getAuthorsByBook(bookEntity));

        return "book";
    }
}
