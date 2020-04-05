package controller;

import daoimpl.BookAuthorDAOImpl;
import daoimpl.BookDAOImpl;
import entity.AuthorEntity;
import entity.BookAuthorEntity;
import entity.BookEntity;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
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

        return "index";
    }
}
