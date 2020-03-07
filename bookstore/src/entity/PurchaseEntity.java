package entity;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "purchase", schema = "public", catalog = "bookstore")
public class PurchaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private int orderId;

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
    private double totalPrice;

    @Basic
    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AccountEntity userId;

    public PurchaseEntity() {}

    public PurchaseEntity(int orderId, Timestamp orderDate,
                          String deliveryAddress, Timestamp deliveryDate,
                          double totalPrice, String orderStatus,
                          AccountEntity userId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.deliveryDate = deliveryDate;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseEntity that = (PurchaseEntity) o;
        return orderId == that.orderId &&
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
