package daoimpl;

import dao.GenericDAO;
import entity.*;
import org.hibernate.Session;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.List;

public class BookDAOImpl extends GenericDAO<BookEntity, Long> {
    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPublicationDate(Date startDate, Date endDate) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE e.publicationDate " +
                        "BETWEEN :startDate AND :endDate")
                .setParameter("startDate", startDate, TemporalType.DATE)
                .setParameter("endDate", endDate, TemporalType.DATE);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByName(String name) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE LOWER(e.bookName) LIKE LOWER(:name)")
                .setParameter("name", '%' + name + '%');
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPageCount(Integer pagesMin, Integer pagesMax) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE e.pageCount BETWEEN :pagesMin AND :pagesMax")
                .setParameter("pagesMin", pagesMin)
                .setParameter("pagesMax", pagesMax);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPrice(Double priceMin, Double priceMax) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE e.bookPrice BETWEEN :priceMin AND :priceMax")
                .setParameter("priceMin", priceMin)
                .setParameter("priceMax", priceMax);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByAvailability(Boolean isAvailable) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e " +
                        "WHERE (e.availableCount = 0 OR :isAvailable IS TRUE) " +
                        "AND NOT (e.availableCount = 0 AND :isAvailable IS TRUE)")
                .setParameter("isAvailable", isAvailable);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByPublisherId(Long publisherId) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e INNER JOIN PublisherEntity p " +
                        "ON e.publisherId = p " +
                        "AND e.publisherId.publisherId = :publisherId")
                .setParameter("publisherId", publisherId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByGenreId(Long genreId) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e INNER JOIN GenreEntity p " +
                        "ON e.genreId = p AND e.genreId.genreId = :genreId")
                .setParameter("genreId", genreId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByCoverTypeId(Long coverTypeId) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e FROM BookEntity e INNER JOIN CoverTypeEntity p " +
                        "ON e.coverTypeId = p " +
                        "AND e.coverTypeId.coverTypeId = :coverTypeId")
                .setParameter("coverTypeId", coverTypeId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getByParameters(String bookName,
                                            Date minPublicationDate, Date maxPublicationDate,
                                            Integer minPages, Integer maxPages,
                                            Double minPrice, Double maxPrice,
                                            Boolean isAvailable,
                                            Long publisherId,
                                            Long genreId,
                                            Long coverTypeId) {
        String q = "SELECT e FROM BookEntity e INNER JOIN PublisherEntity p " +
                "ON e.publisherId = p INNER JOIN GenreEntity g " +
                "ON e.genreId = g INNER JOIN CoverTypeEntity c " +
                "ON e.coverTypeId = c AND ";

        if (bookName != null) {
            q += "LOWER(e.bookName) LIKE LOWER(:bookName) AND ";
        }

        if (minPublicationDate != null) {
            q += "e.publicationDate >= :minPublicationDate AND ";
        }

        if (maxPublicationDate != null) {
            q += "e.publicationDate <= :maxPublicationDate AND ";
        }

        if (minPages != null) {
            q += "e.pageCount >= :minPages AND ";
        }

        if (maxPages != null) {
            q += "e.pageCount <= :maxPages AND ";
        }

        if (minPrice != null) {
            q += "e.bookPrice >= :minPrice AND ";
        }

        if (maxPrice != null) {
            q += "e.bookPrice <= :maxPrice AND ";
        }

        if (publisherId != null) {
            q += "e.publisherId.publisherId = :publisherId AND ";
        }

        if (genreId != null) {
            q += "e.genreId.genreId = :genreId AND ";
        }

        if (coverTypeId != null) {
            q += "e.coverTypeId.coverTypeId = :coverTypeId AND ";
        }

        if (isAvailable != null) {
            q += "(e.availableCount > 0 AND :isAvailable = TRUE OR " +
                    "e.availableCount = 0 AND :isAvailable = FALSE) AND ";
        }

        q += "TRUE = TRUE";

        TypedQuery<BookEntity> query = getSession().createQuery(q);

        if (bookName != null) {
            query.setParameter("bookName", '%' + bookName + '%');
        }

        if (minPublicationDate != null) {
            query.setParameter("minPublicationDate", minPublicationDate);
        }

        if (maxPublicationDate != null) {
            query.setParameter("maxPublicationDate", maxPublicationDate);
        }

        if (minPages != null) {
            query.setParameter("minPages", minPages);
        }

        if (maxPages != null) {
            query.setParameter("maxPages", maxPages);
        }

        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }

        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        if (publisherId != null) {
            query.setParameter("publisherId", publisherId);
        }

        if (genreId != null) {
            query.setParameter("genreId", genreId);
        }

        if (coverTypeId != null) {
            query.setParameter("coverTypeId", coverTypeId);
        }

        if (isAvailable != null) {
            query.setParameter("isAvailable", isAvailable);
        }

        return query.getResultList();
    }

    public void save(BookEntity book, List<String> authorNames,
                     Long publisherId, Long genreId, Long coverTypeId) {
        if (authorNames.isEmpty()) {
            return;
        }

        Session session = getSession();
        AuthorDAOImpl authorDAO = new AuthorDAOImpl();
        BookAuthorDAOImpl bookAuthorDAO = new BookAuthorDAOImpl();
        BookDAOImpl bookDAO = new BookDAOImpl();
        PublisherDAOImpl publisherDAO = new PublisherDAOImpl();
        GenreDAOImpl genreDAO = new GenreDAOImpl();
        CoverTypeDAOImpl coverTypeDAO = new CoverTypeDAOImpl();
        authorDAO.setSession(session);
        bookAuthorDAO.setSession(session);
        bookDAO.setSession(session);
        publisherDAO.setSession(session);
        genreDAO.setSession(session);
        coverTypeDAO.setSession(session);

        book.setPublisherId(publisherDAO.getById(publisherId));
        book.setGenreId(genreDAO.getById(genreId));
        book.setCoverTypeId(coverTypeDAO.getById(coverTypeId));

        bookDAO.save(book);

        for (String name : authorNames) {
            AuthorEntity author;

            if ((author = authorDAO.getByExactName(name)) == null) {
                author = new AuthorEntity(name);
                authorDAO.save(author);
            }

            if (!bookAuthorDAO.getBooksByAuthor(author).contains(book)) {
                bookAuthorDAO.save(new BookAuthorEntity(book, author));
            }
        }
    }
}
