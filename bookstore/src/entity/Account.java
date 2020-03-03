package entity;

public class Account {
    private int userID;
    private String userName;
    private String homeAddress;
    private String phoneNumber;
    private String eMail;
    private String passwordHash;
    private boolean isAdmin;

    public Account() {}
    public Account(int userID, String userName, String homeAddress,
                   String phoneNumber, String eMail, String passwordHash,
                   boolean isAdmin) {
        this.userID = userID;
        this.userName = userName;
        this.homeAddress = homeAddress;
        this.phoneNumber = phoneNumber;
        this.eMail = eMail;
        this.passwordHash = passwordHash;
        this.isAdmin = isAdmin;
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
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
    public boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
