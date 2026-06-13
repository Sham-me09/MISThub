package ui;

public class Admin extends Person {

    private String password;

    public Admin(String username, String password) {

        super(username); 
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}