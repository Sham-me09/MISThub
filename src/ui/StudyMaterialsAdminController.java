package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StudyMaterialsAdminController {

    @FXML
    private VBox materialsBox;

    @FXML
    public void initialize() {
        MaterialStore.loadFromDatabase();
        loadMaterials();
        
    }

    private void loadMaterials() {

        materialsBox.getChildren().clear();

        for (StudyMaterial m : MaterialStore.getMaterials()) {
            materialsBox.getChildren().add(createMaterialRow(m));
        }
    }

    private VBox createMaterialRow(StudyMaterial material) {

        VBox card = new VBox(8);
        card.getStyleClass().add("card");

        Label title = new Label(material.getTitle());
        title.getStyleClass().add("card-title");

        Label info = new Label(
                material.getDepartment() +
                " • Semester " + material.getSemester()
        );

        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("logout-btn");

        deleteBtn.setOnAction(e -> {
    MaterialStore.deleteMaterial(material.getId()); 
    MaterialStore.loadFromDatabase();               
    loadMaterials();
});

        card.getChildren().addAll(title, info, deleteBtn);

        return card;
    }
}