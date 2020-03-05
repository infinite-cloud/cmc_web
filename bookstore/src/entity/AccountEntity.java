package entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account", schema = "public", catalog = "bookstore")
public class AccountEntity {
    private int userId;
    private String userName;
    private String homeAddress;
    private String phoneNumber;
    private String eMail;
    private String passwordHash;
    private boolean isAdmin;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 120)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "home_address", nullable = true, length = -1)
    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    @Basic
    @Column(name = "phone_number", nullable = true, length = 60)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "e_mail", nullable = false, length = 120)
    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    @Basic
    @Column(name = "password_hash", nullable = false, length = 44)
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Basic
    @Column(name = "is_admin", nullable = false)
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return userId == that.userId &&
                isAdmin == that.isAdmin &&
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
