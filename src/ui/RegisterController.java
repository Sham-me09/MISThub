package ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RegisterController {

   
    @FXML private AnchorPane root;

    
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField studentIdField;    
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> deptCombo;

    
    @FXML private Label usernameError;
    @FXML private Label emailError;
    @FXML private Label studentIdError;         
    @FXML private Label passwordError;
    @FXML private Label confirmPasswordError;
    @FXML private Label deptError;

    
    @FXML private Button registerBtn;

    // INITIALIZE 
    @FXML
    public void initialize() {

        // Prevent cursor auto-focus
        Platform.runLater(() -> {
    root.requestFocus();
    
});

        // Hide all errors initially
        hideAllErrors();

        // Department list
        deptCombo.getItems().addAll(
                "CSE","EECE","ME","CE","IPE","NAME",
                "ARCH","BME","EWCE","URP","AE","NSE","PME"
        );

        registerBtn.setDisable(true);

        // Live validation listeners
        usernameField.textProperty().addListener((a,b,c) -> validateForm());
        emailField.textProperty().addListener((a,b,c) -> validateForm());
        studentIdField.textProperty().addListener((a,b,c) -> validateForm()); // ✅
        passwordField.textProperty().addListener((a,b,c) -> validateForm());
        confirmPasswordField.textProperty().addListener((a,b,c) -> validateForm());
        deptCombo.valueProperty().addListener((a,b,c) -> validateForm());
    }

    //  REGISTER 
   @FXML
void handleRegister(ActionEvent event) {

    if (validateForm()) {

        boolean success = UserDAO.registerUser(
                usernameField.getText(),
                emailField.getText(),
                studentIdField.getText(),
                passwordField.getText(),
                deptCombo.getValue()
        );

        if (success) {

            try {
                
                Session.setUser(usernameField.getText());

               
                Stage stage = (Stage) root.getScene().getWindow();
                SceneSwitcher.switchScene(stage, "/ui/UserDashboard.fxml");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            showAlert("Registration Failed", "User could not be registered.");
        }
    }
}

    //  VALIDATION
    private boolean validateForm() {

        hideAllErrors();
        boolean valid = true;

        // Username
        if (usernameField.getText().isEmpty()) {
            usernameError.setText("Username required");
            usernameError.setVisible(true);
            valid = false;
        }

        // Email
        if (!emailField.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            emailError.setText("Invalid email");
            emailError.setVisible(true);
            valid = false;
        }

        // Student ID
        if (!studentIdField.getText().matches("\\d{6,}")) {
            studentIdError.setText("Student ID must be at least 6 digits");
            studentIdError.setVisible(true);
            valid = false;
        }

        // Password
        if (passwordField.getText().length() < 6) {
            passwordError.setText("At least 6 characters");
            passwordError.setVisible(true);
            valid = false;
        }

        // Confirm password
        if (!confirmPasswordField.getText().equals(passwordField.getText())) {
            confirmPasswordError.setText("Passwords do not match");
            confirmPasswordError.setVisible(true);
            valid = false;
        }

        // Department
        if (deptCombo.getValue() == null) {
            deptError.setText("Select department");
            deptError.setVisible(true);
            valid = false;
        }

        registerBtn.setDisable(!valid);
        return valid;
    }

    //HELPERS 
    private void hideAllErrors() {
        usernameError.setVisible(false);
        emailError.setVisible(false);
        studentIdError.setVisible(false);  
        passwordError.setVisible(false);
        confirmPasswordError.setVisible(false);
        deptError.setVisible(false);
    }

    //  NAVIGATION 
    @FXML
    void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
    SceneSwitcher.switchScene(stage, "/ui/UserLogin.fxml");
    }

    @FXML
    void goToLogin(MouseEvent event) throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
    SceneSwitcher.switchScene(stage, "/ui/UserLogin.fxml");
    }

    private void loadPage(String fxml) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) this.root.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    private void showAlert(String title, String msg) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(msg);
    alert.showAndWait();
}
    

    
}
