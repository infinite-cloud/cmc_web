package entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "public", catalog = "bookstore")
public class BookEntity {
    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private int bookId;

    @Basic
    @Column(name = "book_name", nullable = false, length = 240)
    private String bookName;

    @Basic
    @Column(name = "publication_date", nullable = false)
    private Date publicationDate;

    @Basic
    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Basic
    @Column(name = "book_price", nullable = false, precision = 0)
    private double bookPrice;

    @Basic
    @Column(name = "available_count", nullable = false)
    private int availableCount;

    @Basic
    @Column(name = "description", nullable = false, length = -1)
    private String description;

    @OneToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private PublisherEntity publisherId;

    @OneToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEntity genreId;

    @OneToOne
    @JoinColumn(name = "cover_type_id", nullable = false)
    private CoverTypeEntity coverTypeId;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
    }

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

    public PublisherEntity getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(PublisherEntity publisherId) {
        this.publisherId = publisherId;
    }

    public GenreEntity getGenreId() {
        return genreId;
    }

    public void setGenreId(GenreEntity genreId) {
        this.genreId = genreId;
    }

    public CoverTypeEntity getCoverTypeId() {
        return coverTypeId;
    }

    public void setCoverTypeId(CoverTypeEntity coverTypeId) {
        this.coverTypeId = coverTypeId;
    }
}
