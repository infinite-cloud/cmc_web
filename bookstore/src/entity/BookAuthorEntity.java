package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "book_author", schema = "public", catalog = "bookstore")
public class BookAuthorEntity implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity bookId;

    @Id
    @OneToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity authorId;

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
        return bookId == that.bookId &&
                authorId == that.authorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, authorId);
    }
}
