package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookAuthorId implements Serializable {
    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "book_id")
    private Long bookId;

    public BookAuthorId() {}

    public BookAuthorId(Long authorId, Long bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getBookId() {
        return bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BookAuthorId that = (BookAuthorId) o;
        return Objects.equals(getAuthorId(), that.getAuthorId()) &&
                Objects.equals(getBookId(), that.getBookId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getAuthorId());
    }
}
