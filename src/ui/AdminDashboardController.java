package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import java.awt.Desktop;
import java.util.List;

public class AdminDashboardController {

    @FXML
    private StackPane contentArea;
    
    @FXML
private Label welcomeLabel;

    //  INITIAL LOAD 
    @FXML
public void initialize() throws Exception {

    welcomeLabel.setText(
        "Welcome, " + Session.getUser() + " 👋"
    );

    loadContent("/ui/AdminHome.fxml");
}

    //  CONTENT SWITCHER 
    private void loadContent(String fxml) throws Exception {

        Parent content = FXMLLoader.load(
                getClass().getResource(fxml)
        );

        contentArea.getChildren().clear();
        contentArea.getChildren().add(content);
    }

    private void loadPage(String fxml) throws Exception {
    Parent page = FXMLLoader.load(getClass().getResource(fxml));
    contentArea.getChildren().setAll(page);
}
    // SIDEBAR ACTIONS 
    @FXML
   
private void showDashboard() throws Exception {
    loadPage("/ui/AdminHome.fxml");
}
    @FXML
    private void showUsers(ActionEvent e) throws Exception {
        loadContent("/ui/ManageUsers.fxml");
    }


    @FXML
private void showAdminMaterials() {
        MaterialStore.loadFromDatabase();
    contentArea.getChildren().clear();

    VBox layout = new VBox(15);

    Label title = new Label("Manage Study Materials");
    title.getStyleClass().add("page-title");

    VBox materialsBox = new VBox(10);

    // Load materials from DB
    renderAdminMaterials(MaterialStore.getMaterials(), materialsBox);

    layout.getChildren().addAll(title, materialsBox);
    contentArea.getChildren().add(layout);
}



private void renderAdminMaterials(List<StudyMaterial> list, VBox box) {

    box.getChildren().clear();

    for (StudyMaterial m : list) {
        box.getChildren().add(createAdminMaterialCard(m));
    }
}


private VBox createAdminMaterialCard(StudyMaterial material) {

    VBox card = new VBox(8);
    card.getStyleClass().add("card");

    Label title = new Label(material.getTitle());
    title.getStyleClass().add("card-title");

    Label info = new Label(
            material.getDepartment() + " • Semester "
            + material.getSemester() + " • "
            + material.getType()
    );

    Label uploader = new Label(
            "Uploaded by: " + material.getUploadedBy()
    );

    // VIEW BUTTON
    Button viewBtn = new Button("📄 View");
    viewBtn.getStyleClass().add("login-button");

    viewBtn.setOnAction(e -> {
        try {
            if (material.getFile() != null) {
                Desktop.getDesktop().open(material.getFile());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    });

    // DELETE BUTTON (ADMIN POWER)
    Button deleteBtn = new Button("🗑 Delete");
    deleteBtn.getStyleClass().add("danger-button");

    deleteBtn.setOnAction(e -> {

    MaterialStore.deleteMaterial(material.getId());

    // reload from DB
    MaterialStore.loadFromDatabase();

    // rebuild admin materials page
    showAdminMaterials();
});
    HBox buttons = new HBox(10, viewBtn, deleteBtn);

    card.getChildren().addAll(title, info, uploader, buttons);

    return card;
}
       



    @FXML
private void showProjects() throws Exception {
    loadPage("/ui/AdminProjects.fxml");
}

    @FXML
    private void showResearch(ActionEvent e) throws Exception {
        loadContent("/ui/AdminResearch.fxml");
    }

    @FXML
    private void showAlumni(ActionEvent e) throws Exception {
        loadContent("/ui/AdminAlumni.fxml");
    }

    // LOGOUT
    @FXML
    private void handleLogout(ActionEvent event) throws Exception {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/ui/Welcome.fxml");
    }
}