package ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class AdminProjectsController {

    @FXML private VBox projectsBox;

    @FXML
    public void initialize() {
        loadProjects();
    }

    private void loadProjects() {

        ProjectStore.loadFromDatabase();
        projectsBox.getChildren().clear();

        for (Project p : ProjectStore.getProjects()) {

            VBox card = new VBox(8);
            card.getStyleClass().add("card");

            Label title = new Label(p.getTitle());
            Label skills = new Label("Skills: " + p.getSkills());

            Button deleteBtn = new Button("Delete");
            deleteBtn.getStyleClass().add("danger-button");

            deleteBtn.setOnAction(e -> {
                ProjectStore.deleteProject(p.getId());
                loadProjects();
            });

            card.getChildren().addAll(title, skills, deleteBtn);

            projectsBox.getChildren().add(card);
        }
    }
}