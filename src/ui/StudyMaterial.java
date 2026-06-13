package ui;
import java.io.File;

public class StudyMaterial {

    private String title;
    private String department;
    private String semester;
    private String type;
    private String uploadedBy;
    private File file;
    private String filePath;
 private int id;

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}


public File getFile() {
    return file;
}

public void setFile(File file) {
    this.file = file;
}

    public StudyMaterial(String title, String department, String semester, String type, String uploadedBy) {
        this.title = title;
        this.department = department;
        this.semester = semester;
        this.type = type;
        this.uploadedBy = uploadedBy;
        
    }

    public String getTitle() {
        return title;
    }

    public String getDepartment() {
        return department;
    }

    public String getSemester() {
        return semester;
    }

    public String getType() {
        return type;
    }
    
    public String getUploadedBy() {
    return uploadedBy;
}
    public String getFilePath() {
    return filePath;
}

public void setFilePath(String path) {
    this.filePath = path;
}

}

