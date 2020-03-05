package entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "public", catalog = "bookstore")
public class BookEntity {
    private int bookId;
    private String bookName;
    private Date publicationDate;
    private int pageCount;
    private BigInteger bookPrice;
    private int availableCount;
    private String description;
    private int publisherId;
    private int genreId;
    private int coverTypeId;

    @Id
    @Column(name = "book_id", nullable = false)
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "book_name", nullable = false, length = 240)
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Basic
    @Column(name = "publication_date", nullable = false)
    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Basic
    @Column(name = "page_count", nullable = false)
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Basic
    @Column(name = "book_price", nullable = false, precision = 0)
    public BigInteger getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigInteger bookPrice) {
        this.bookPrice = bookPrice;
    }

    @Basic
    @Column(name = "available_count", nullable = false)
    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return bookId == that.bookId &&
                pageCount == that.pageCount &&
                availableCount == that.availableCount &&
                Objects.equals(bookName, that.bookName) &&
                Objects.equals(publicationDate, that.publicationDate) &&
                Objects.equals(bookPrice, that.bookPrice) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookName, publicationDate, pageCount, bookPrice, availableCount, description);
    }

    @Basic
    @Column(name = "publisher_id", nullable = false)
    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "genre_id", nullable = false)
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Basic
    @Column(name = "cover_type_id", nullable = false)
    public int getCoverTypeId() {
        return coverTypeId;
    }

    public void setCoverTypeId(int coverTypeId) {
        this.coverTypeId = coverTypeId;
    }
}
