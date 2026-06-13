package ui;

public class User extends Person {

    private String id;
    private String email;
    private String department;

    public User(String id, String username,
                String email, String department) {

        super(username); 

        this.id = id;
        this.email = email;
        this.department = department;
    }

    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
}