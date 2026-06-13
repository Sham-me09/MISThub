package ui;

public class Alumni {

    private String name;
    private String education;
    private String position;
    private String research;
    private String email;
    private String jobs;
    private String summary;
    private String imagePath;

    public Alumni(String name, String education, String position,
                  String research, String email,
                  String jobs, String summary, String imagePath) {

        this.name = name;
        this.education = education;
        this.position = position;
        this.research = research;
        this.email = email;
        this.jobs = jobs;
        this.summary = summary;
        this.imagePath = imagePath;
    }

    // getters
    public String getName(){ return name; }
    public String getEducation(){ return education; }
    public String getPosition(){ return position; }
    public String getResearch(){ return research; }
    public String getEmail(){ return email; }
    public String getJobs(){ return jobs; }
    public String getSummary(){ return summary; }
    public String getImagePath(){ return imagePath; }
}