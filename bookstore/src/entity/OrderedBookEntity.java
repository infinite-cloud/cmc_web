package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ordered_book", schema = "public", catalog = "bookstore")
public class OrderedBookEntity implements Serializable {
    @Basic
    @Column(name = "book_count", nullable = false)
    private int bookCount;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private PurchaseEntity orderId;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity bookId;

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

    public PurchaseEntity getOrderId() {
        return orderId;
    }

    public void setOrderId(PurchaseEntity orderId) {
        this.orderId = orderId;
    }

    public BookEntity getBookId() {
        return bookId;
    }

    public void setBookId(BookEntity bookId) {
        this.bookId = bookId;
    }
}
