package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account", schema = "public", catalog = "bookstore")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Basic
    @Column(name = "user_name", nullable = false, length = 120)
    private String userName;

    @Basic
    @Column(name = "home_address", nullable = true, length = -1)
    private String homeAddress;

    @Basic
    @Column(name = "phone_number", nullable = true, length = 60)
    private String phoneNumber;

    @Basic
    @Column(name = "e_mail", nullable = false, length = 120)
    private String eMail;

    @Basic
    @Column(name = "password_hash", nullable = false, length = 60)
    private String passwordHash;

    @Basic
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    public AccountEntity() {}

    public AccountEntity(String userName, String homeAddress,
                         String phoneNumber, String eMail, String passwordHash,
                         Boolean isAdmin) {
        this.userName = userName;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(isAdmin, that.isAdmin) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(homeAddress, that.homeAddress) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(eMail, that.eMail) &&
                Objects.equals(passwordHash, that.passwordHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, homeAddress, phoneNumber, eMail, passwordHash, isAdmin);
    }
}
