package ui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;

public class AdminResearchController {

    @FXML private VBox researchBox;

    @FXML
    public void initialize() {
        loadResearch();
    }

    private void loadResearch() {

        ResearchStore.loadFromDatabase();
        researchBox.getChildren().clear();

        for (Research r : ResearchStore.getResearch()) {

            VBox card = new VBox(8);
            card.getStyleClass().add("card");

            Label title = new Label(r.getTitle());
            Label faculty = new Label("Faculty: " + r.getFaculty());

            Button deleteBtn = new Button("Delete");
            deleteBtn.getStyleClass().add("danger-button");

            deleteBtn.setOnAction(e -> {
                ResearchStore.deleteResearch(r.getId());
                loadResearch();
            });

            card.getChildren().addAll(title, faculty, deleteBtn);

            researchBox.getChildren().add(card);
        }
    }
}