package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author", schema = "public", catalog = "bookstore")
public class AuthorEntity {
    @Id
    @GeneratedValue
    @Column(name = "author_id")
    private int authorId;

    @Basic
    @Column(name = "author_name", nullable = false, length = 120)
    private String authorName;

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return authorId == that.authorId &&
                Objects.equals(authorName, that.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, authorName);
    }
}
