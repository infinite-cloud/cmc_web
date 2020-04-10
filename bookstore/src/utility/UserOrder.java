package utility;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserOrder {
    private Long id;
    private Double price;
    private String status;
    private String orderDate;
    private String deliveryDate;
    private List<Pair<String, Integer>> books;

    public UserOrder() {
        books = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<Pair<String, Integer>> getBooks() {
        return books;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setBooks(List<Pair<String, Integer>> books) {
        this.books = books;
    }
}
