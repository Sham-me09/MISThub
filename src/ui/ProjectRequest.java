package ui;

public class ProjectRequest {

    private int projectId;
    private String username;
    private String projectTitle;

public ProjectRequest(int projectId, String username, String projectTitle){
    this.projectId = projectId;
    this.username = username;
    this.projectTitle = projectTitle;
}

    public String getProjectTitle(){
    return projectTitle;
}
    public int getProjectId(){
        return projectId;
    }

    public String getUsername(){
        return username;
    }
}