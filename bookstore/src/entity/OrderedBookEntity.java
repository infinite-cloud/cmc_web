package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ordered_book", schema = "public", catalog = "bookstore")
public class OrderedBookEntity implements Serializable {
    @Basic
    @Column(name = "book_count", nullable = false)
    private Integer bookCount;

    @EmbeddedId
    private OrderedBookId id;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private PurchaseEntity order;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private BookEntity book;

    public OrderedBookEntity() {}

    public OrderedBookEntity(Integer bookCount, PurchaseEntity order,
                             BookEntity book) {
        this.bookCount = bookCount;
        this.order = order;
        this.book = book;
    }

    public OrderedBookId getId() {
        return id;
    }

    public void setId(OrderedBookId id) {
        this.id = id;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
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

    public PurchaseEntity getOrder() {
        return order;
    }

    public void setOrder(PurchaseEntity order) {
        this.order = order;
    }

    public BookEntity getBookId() {
        return book;
    }

    public void setBookId(BookEntity bookId) {
        this.book = book;
    }
}
