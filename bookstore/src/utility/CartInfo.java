package utility;

import entity.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class CartInfo {
    private List<CartItem> bookList;
    private Long orderId;

    public CartInfo() {
        bookList = new ArrayList<>();
        orderId = (long) -1;
    }

    public void addItem(BookEntity bookEntity) {
        for (CartItem cartItem : bookList) {
            if (cartItem.getBook().getBookId().equals(bookEntity.getBookId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                return;
            }
        }

        bookList.add(new CartItem(bookEntity, 1));
    }

    public List<CartItem> getBookList() {
        return bookList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setBookList(List<CartItem> bookList) {
        this.bookList = bookList;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getTotalPrice() {
        double total = 0.0;

        for (CartItem item : bookList) {
            total += item.getBook().getBookPrice() * item.getQuantity();
        }

        return total;
    }

    public CartItem getItem(Long id) {
        for (CartItem cartItem : bookList) {
            if (cartItem.getBook().getBookId().equals(id)) {
                return cartItem;
            }
        }

        return null;
    }

    public void removeItem(Long id) {
        bookList.removeIf(item -> item.getBook().getBookId().equals(id));
    }

    public void updateItem(Long id, Integer quantity) {
        for (CartItem cartItem : bookList) {
            if (cartItem.getBook().getBookId().equals(id)) {
                if (quantity > 0) {
                    cartItem.setQuantity(quantity);
                } else {
                    bookList.remove(cartItem);
                }

                return;
            }
        }
    }

    public void updateQuantity(CartInfo cartForm) {
        if (cartForm != null) {
            for (CartItem item : cartForm.getBookList()) {
                updateItem(item.getId(), item.getQuantity());
            }
        }
    }

    public void clearCart() {
        bookList.clear();
    }
}
