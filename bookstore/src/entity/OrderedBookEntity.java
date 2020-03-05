package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ordered_book", schema = "public", catalog = "bookstore")
public class OrderedBookEntity implements Serializable {
    private int bookCount;
    private int orderId;
    private int bookId;

    @Basic
    @Column(name = "book_count", nullable = false)
    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedBookEntity that = (OrderedBookEntity) o;
        return bookCount == that.bookCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookCount);
    }

    @Id
    @Column(name = "order_id", nullable = false)
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Id
    @Column(name = "book_id", nullable = false)
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
