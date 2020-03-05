package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author", schema = "public", catalog = "bookstore")
public class AuthorEntity {
    private int authorId;
    private String authorName;

    @Id
    @Column(name = "author_id", nullable = false)
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "author_name", nullable = false, length = 120)
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
