package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminHomeController {

    @FXML private Label usersCount;
    @FXML private Label materialsCount;
    @FXML private Label projectsCount;
    @FXML private Label researchCount;

    @FXML
    public void initialize() {

        usersCount.setText(String.valueOf(AdminStats.totalUsers()));
        materialsCount.setText(String.valueOf(AdminStats.totalStudyMaterials()));
        projectsCount.setText(String.valueOf(AdminStats.totalProjects()));
        researchCount.setText(String.valueOf(AdminStats.totalResearch()));
    }
}