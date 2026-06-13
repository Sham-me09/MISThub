package ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AdminAlumniController {

    @FXML private TextField nameField;
    @FXML private TextField educationField;
    @FXML private TextField positionField;
    @FXML private TextField researchField;
    @FXML private TextField emailField;
    @FXML private TextField jobsField;
    @FXML private TextArea summaryField;
    @FXML private TextField imagePathField;

    @FXML private VBox alumniBox;

    @FXML
    public void initialize() {
        loadAlumni();
    }

    // ===== ADD =====
    @FXML
    private void handleAddAlumni() {

        Alumni a = new Alumni(
                nameField.getText(),
                educationField.getText(),
                positionField.getText(),
                researchField.getText(),
                emailField.getText(),
                jobsField.getText(),
                summaryField.getText(),
                imagePathField.getText()
        );

        AlumniDAO.addAlumni(a);

        clearFields();
        loadAlumni();
    }

    private void clearFields() {
        nameField.clear();
        educationField.clear();
        positionField.clear();
        researchField.clear();
        emailField.clear();
        jobsField.clear();
        summaryField.clear();
        imagePathField.clear();
    }

    // ===== LOAD =====
    private void loadAlumni() {

        alumniBox.getChildren().clear();

        for (Alumni a : AlumniDAO.getAllAlumni()) {

    VBox card = new VBox(6);
    card.getStyleClass().add("card");

    Label name = new Label("👤 " + a.getName());
    Label pos = new Label("💼 " + a.getPosition());

    Button deleteBtn = new Button("🗑 Delete");
    deleteBtn.getStyleClass().add("danger-button");

    deleteBtn.setOnAction(e -> {
        AlumniDAO.deleteAlumni(a.getEmail()); // use unique field
        loadAlumni();
    });

    card.getChildren().addAll(name, pos, deleteBtn);

    alumniBox.getChildren().add(card);
}
    }
}