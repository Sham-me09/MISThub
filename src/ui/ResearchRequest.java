package ui;

public class ResearchRequest {

    private int researchId;
    private String username;
    private String researchTitle;

    public ResearchRequest(int researchId, String username, String researchTitle){
        this.researchId = researchId;
        this.username = username;
        this.researchTitle = researchTitle;
    }

    public int getResearchId(){
        return researchId;
    }

    public String getUsername(){
        return username;
    }

    public String getResearchTitle(){
        return researchTitle;
    }
}