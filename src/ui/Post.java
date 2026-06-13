package ui;

public class Post {
    protected int id;
    protected String title;
    protected String createdBy;

    public Post(int id, String title, String createdBy) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getCreatedBy() { return createdBy; }
}