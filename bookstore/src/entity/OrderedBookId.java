package entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderedBookId implements Serializable {
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "book_id")
    private Long bookId;

    public OrderedBookId() {}

    public OrderedBookId(Long orderId, Long bookId) {
        this.orderId = orderId;
        this.bookId = bookId;
    }

    public Long getOrderId() {
        return orderId;
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
        OrderedBookId that = (OrderedBookId) o;
        return Objects.equals(getOrderId(), that.getOrderId()) &&
                Objects.equals(getBookId(), that.getBookId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookId(), getOrderId());
    }
}
