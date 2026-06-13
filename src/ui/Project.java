package ui;

public class Project extends Post {

    private String skills;
    private String status;

    public Project(int id, String title, String skills,
                   String status, String createdBy) {

        super(id, title, createdBy); 

        this.skills = skills;
        this.status = status;
    }

    public String getSkills() { return skills; }
    public String getStatus() { return status; }
}