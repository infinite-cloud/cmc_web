package utility;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BookForm {
    private String bookName;
    private Date publicationDate;
    private Integer pageCount;
    private Double bookPrice;
    private Integer availableCount;
    private String description;
    private Long publisherId;
    private Long genreId;
    private Long coverTypeId;
    private String imageName;
    private List<Long> bookAuthors;

    public BookForm() {
        this.bookAuthors = new ArrayList<>();
    }

    public String getBookName() {
        return bookName;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public Double getBookPrice() {
        return bookPrice;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public String getDescription() {
        return description;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public Long getCoverTypeId() {
        return coverTypeId;
    }

    public String getImageName() {
        return imageName;
    }

    public List<Long> getBookAuthors() {
        return bookAuthors;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public void setBookPrice(Double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public void setCoverTypeId(Long coverTypeId) {
        this.coverTypeId = coverTypeId;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setBookAuthors(List<Long> bookAuthors) {
        this.bookAuthors = bookAuthors;
    }
}
