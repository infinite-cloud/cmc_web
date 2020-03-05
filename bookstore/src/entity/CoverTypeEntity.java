package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cover_type", schema = "public", catalog = "bookstore")
public class CoverTypeEntity {
    private int coverTypeId;
    private String coverTypeName;

    @Id
    @Column(name = "cover_type_id", nullable = false)
    public int getCoverTypeId() {
        return coverTypeId;
    }

    public void setCoverTypeId(int coverTypeId) {
        this.coverTypeId = coverTypeId;
    }

    @Basic
    @Column(name = "cover_type_name", nullable = false, length = 240)
    public String getCoverTypeName() {
        return coverTypeName;
    }

    public void setCoverTypeName(String coverTypeName) {
        this.coverTypeName = coverTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoverTypeEntity that = (CoverTypeEntity) o;
        return coverTypeId == that.coverTypeId &&
                Objects.equals(coverTypeName, that.coverTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coverTypeId, coverTypeName);
    }
}
