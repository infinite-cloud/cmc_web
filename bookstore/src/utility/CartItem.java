package utility;

import entity.BookEntity;

public class CartItem {
    BookEntity book;
    Long id;
    Integer quantity;

    public CartItem() {}

    public CartItem(BookEntity book, Integer quantity) {
        this.book = book;
        this.id = book.getBookId();
        this.quantity = quantity;
    }

    public BookEntity getBook() {
        return book;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
