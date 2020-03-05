package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "genre", schema = "public", catalog = "bookstore")
public class GenreEntity {
    private int genreId;
    private String genreName;

    @Id
    @Column(name = "genre_id", nullable = false)
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Basic
    @Column(name = "genre_name", nullable = false, length = 240)
    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return genreId == that.genreId &&
                Objects.equals(genreName, that.genreName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genreId, genreName);
    }
}
