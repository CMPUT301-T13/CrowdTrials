package main.java.com.example.crowdtrials;

public class ContactInfo {
    private String email;
    private String phoneNum;

    public ContactInfo(String email, String phoneNum) {
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
