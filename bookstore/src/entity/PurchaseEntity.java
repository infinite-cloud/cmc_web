package entity;

import enumtype.PostgreSQLEnumType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "purchase", schema = "public", catalog = "bookstore")
@TypeDef(name = "pgsql_enum", typeClass = PostgreSQLEnumType.class)
public class PurchaseEntity {
    public enum OrderStatus {
        IN_PROCESSING,
        READY,
        DELIVERED,
        CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Basic
    @Column(name = "order_date", nullable = false)
    private Timestamp orderDate;

    @Basic
    @Column(name = "delivery_address", nullable = false, length = -1)
    private String deliveryAddress;

    @Basic
    @Column(name = "delivery_date", nullable = false)
    private Timestamp deliveryDate;

    @Basic
    @Column(name = "total_price", nullable = false, precision = 0)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    @Type(type = "pgsql_enum")
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AccountEntity userId;

    public PurchaseEntity() {}

    public PurchaseEntity(Timestamp orderDate,
                          String deliveryAddress, Timestamp deliveryDate,
                          Double totalPrice, OrderStatus orderStatus,
                          AccountEntity userId) {
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(orderDate, that.orderDate) &&
                Objects.equals(deliveryAddress, that.deliveryAddress) &&
                Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(totalPrice, that.totalPrice) &&
                Objects.equals(orderStatus, that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDate, deliveryAddress, deliveryDate, totalPrice, orderStatus);
    }

    public AccountEntity getUserId() {
        return userId;
    }

    public void setUserId(AccountEntity userId) {
        this.userId = userId;
    }
}
