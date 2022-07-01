
public class User {
    private String fName;
    private String mName;
    private String lName;
    private String userName;
    private String userPass;
    private float balance;

    public User(String fName, String mName, String lName, String userName, String userPass, float balance) {
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.userName = userName;
        this.userPass = userPass;
        this.balance = balance;
    }

    public String getFName() {
        return this.fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getMName() {
        return this.mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getLName() {
        return this.lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return this.userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public float getBalance() {
        return this.balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
