package ui;

public class Research extends Post {

    private String faculty;
    private String department;
    private String description;

    public Research(int id, String title,
                    String faculty,
                    String department,
                    String description,
                    String createdBy) {

        super(id, title, createdBy); 

        this.faculty = faculty;
        this.department = department;
        this.description = description;
    }

    public String getFaculty() { return faculty; }
    public String getDepartment() { return department; }
    public String getDescription() { return description; }
}