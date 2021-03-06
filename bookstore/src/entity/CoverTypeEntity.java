package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cover_type", schema = "public", catalog = "bookstore")
public class CoverTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_type_id")
    private Long coverTypeId;

    @Basic
    @Column(name = "cover_type_name", nullable = false, length = 240)
    private String coverTypeName;

    public CoverTypeEntity() {}

    public CoverTypeEntity(String coverTypeName) {
        this.coverTypeName = coverTypeName;
    }

    public Long getCoverTypeId() {
        return coverTypeId;
    }

    public void setCoverTypeId(Long coverTypeId) {
        this.coverTypeId = coverTypeId;
    }

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
        return Objects.equals(coverTypeId, that.coverTypeId) &&
                Objects.equals(coverTypeName, that.coverTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coverTypeId, coverTypeName);
    }
}
