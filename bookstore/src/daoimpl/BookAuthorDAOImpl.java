package daoimpl;

import dao.GenericDAO;
import entity.AuthorEntity;
import entity.BookAuthorEntity;
import entity.BookAuthorId;
import entity.BookEntity;

import javax.persistence.TypedQuery;
import java.util.List;

public class BookAuthorDAOImpl
        extends GenericDAO<BookAuthorEntity, BookAuthorId> {
    public BookAuthorEntity getByCompositeId(Long bookId, Long authorId) {
        return getById(new BookAuthorId(authorId, bookId));
    }

    @SuppressWarnings("unchecked")
    public List<BookEntity> getBooksByAuthor(AuthorEntity author) {
        TypedQuery<BookEntity> query = getSession().createQuery(
                "SELECT e.bookId FROM BookAuthorEntity e " +
                        "WHERE e.id.authorId = :authorId")
                .setParameter("authorId", author.getAuthorId());
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<AuthorEntity> getAuthorsByBook(BookEntity book) {
        TypedQuery<AuthorEntity> query = getSession().createQuery(
                "SELECT e.authorId FROM BookAuthorEntity e " +
                        "WHERE e.bookId = :book")
                .setParameter("book", book);
        return query.getResultList();

    }
}
