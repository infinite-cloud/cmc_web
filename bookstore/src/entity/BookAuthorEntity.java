package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "book_author", schema = "public", catalog = "bookstore")
public class BookAuthorEntity implements Serializable {
    @EmbeddedId
    BookAuthorId id;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private BookEntity bookId;

    @ManyToOne
    @JoinColumn(name = "author_id", insertable = false, updatable = false)
    private AuthorEntity authorId;

    public BookAuthorEntity() {}

    public BookAuthorEntity(BookEntity bookId, AuthorEntity authorId) {
        this.id = new BookAuthorId(authorId.getAuthorId(), bookId.getBookId());
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public BookAuthorId getId() {
        return id;
    }

    public void setId(BookAuthorId id) {
        this.id = id;
    }

    public BookEntity getBookId() {
        return bookId;
    }

    public void setBookId(BookEntity bookId) {
        this.bookId = bookId;
    }

    public AuthorEntity getAuthorId() {
        return authorId;
    }

    public void setAuthorId(AuthorEntity authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAuthorEntity that = (BookAuthorEntity) o;
        return Objects.equals(bookId, that.bookId) &&
                Objects.equals(authorId, that.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, authorId);
    }
}
