package utility;

public class UserForm {
    private String eMail;
    private String password;
    private String repeatedPassword;
    private String userName;
    private String homeAddress;
    private String phoneNumber;
    private Boolean needsInitialValidation;

    public UserForm() {
        this.password = "";
        this.repeatedPassword = "";
    }

    public String geteMail() {
        return eMail;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getNeedsInitialValidation() {
        return needsInitialValidation;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNeedsInitialValidation(Boolean needsInitialValidation) {
        this.needsInitialValidation = needsInitialValidation;
    }
}
