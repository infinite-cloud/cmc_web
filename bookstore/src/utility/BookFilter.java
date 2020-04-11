package utility;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookFilter {
    private String name;
    private Long genre;
    private Long cover;
    private Long publisher;
    private Boolean availability;
    private Double minPrice;
    private Double maxPrice;
    private Integer minPages;
    private Integer maxPages;
    private Date minDate;
    private Date maxDate;
    private Map<Integer, String> authors;

    public BookFilter() {
        authors = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Long getGenre() {
        return genre;
    }

    public Long getCover() {
        return cover;
    }

    public Long getPublisher() {
        return publisher;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Integer getMinPages() {
        return minPages;
    }

    public Integer getMaxPages() {
        return maxPages;
    }

    public Date getMinDate() {
        return minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public Map<Integer, String> getAuthors() {
        return authors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(Long genre) {
        this.genre = genre;
    }

    public void setCover(Long cover) {
        this.cover = cover;
    }

    public void setPublisher(Long publisher) {
        this.publisher = publisher;
    }

    public void setAvailability(Boolean availability) {
        this.availability = (availability) ? availability : null;
    }

    public void setMinPrice(Double priceMin) {
        this.minPrice = priceMin;
    }

    public void setMaxPrice(Double priceMax) {
        this.maxPrice = priceMax;
    }

    public void setMinPages(Integer minPages) {
        this.minPages = minPages;
    }

    public void setMaxPages(Integer maxPages) {
        this.maxPages = maxPages;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void setAuthors(Map<Integer, String> authors) {
        this.authors = authors;
    }
}
