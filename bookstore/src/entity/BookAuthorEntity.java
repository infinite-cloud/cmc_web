package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "book_author", schema = "public", catalog = "bookstore")
public class BookAuthorEntity implements Serializable {
    private int bookId;
    private int authorId;

    @Id
    @Column(name = "book_id", nullable = false)
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Id
    @Column(name = "author_id", nullable = false)
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
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
