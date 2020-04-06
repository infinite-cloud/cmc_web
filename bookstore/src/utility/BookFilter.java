package utility;

import java.sql.Date;

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
    private Integer minYear;
    private Integer maxYear;

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

    public Integer getMinYear() {
        return minYear;
    }

    public Integer getMaxYear() {
        return maxYear;
    }

    public Date getMinDate() {
        return (minYear != null) ? Date.valueOf(minYear + "-01-01") : null;
    }

    public Date getMaxDate() {
        return (maxYear != null) ? Date.valueOf(maxYear + "-12-31") : null;
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

    public void setMinYear(Integer year) {
        this.minYear = year;
    }

    public void setMaxYear(Integer year) {
        this.maxYear = year;
    }
}
