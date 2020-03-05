package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "publisher", schema = "public", catalog = "bookstore")
public class PublisherEntity {
    private int publisherId;
    private String publisherName;

    @Id
    @Column(name = "publisher_id", nullable = false)
    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "publisher_name", nullable = false, length = 120)
    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublisherEntity that = (PublisherEntity) o;
        return publisherId == that.publisherId &&
                Objects.equals(publisherName, that.publisherName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publisherId, publisherName);
    }
}
