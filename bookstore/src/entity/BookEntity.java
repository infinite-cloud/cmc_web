package entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "public", catalog = "bookstore")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Basic
    @Column(name = "book_name", nullable = false, length = 240)
    private String bookName;

    @Basic
    @Column(name = "publication_date", nullable = false)
    private Date publicationDate;

    @Basic
    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    @Basic
    @Column(name = "book_price", nullable = false, precision = 0)
    private Double bookPrice;

    @Basic
    @Column(name = "available_count", nullable = false)
    private Integer availableCount;

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

    public BookEntity() {}

    public BookEntity(String bookName, Date publicationDate,
                      Integer pageCount, Double bookPrice,
                      Integer availableCount, String description,
                      PublisherEntity publisherId, GenreEntity genreId,
                      CoverTypeEntity coverTypeId) {
        this.bookName = bookName;
        this.publicationDate = publicationDate;
        this.pageCount = pageCount;
        this.bookPrice = bookPrice;
        this.availableCount = availableCount;
        this.description = description;
        this.publisherId = publisherId;
        this.genreId = genreId;
        this.coverTypeId = coverTypeId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
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

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
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
        return Objects.equals(bookId, that.bookId) &&
                Objects.equals(pageCount, that.pageCount) &&
                Objects.equals(availableCount, that.availableCount) &&
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
