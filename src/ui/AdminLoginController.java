package ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.*;



public class AdminLoginController {

    //  Root 
    @FXML private AnchorPane root;

    //  INITIALIZE 
    @FXML
    public void initialize() {

       // Prevent auto focus on text fields
    javafx.application.Platform.runLater(() -> root.requestFocus());
    }

    // LOGIN 
   @FXML
private TextField loginField;

@FXML
private PasswordField passwordField;

@FXML
private Label loginError;

@FXML
private Label passwordError;


@FXML
private void handleLogin(ActionEvent event) throws Exception {

    String username = loginField.getText();
    String password = passwordField.getText();

    // validation
    if (username.isEmpty()) {
        loginError.setText("Username required");
        loginError.setVisible(true);
        return;
    }

    if (password.isEmpty()) {
        passwordError.setText("Password required");
        passwordError.setVisible(true);
        return;
    }

    boolean success =
            AdminDAO.loginAdmin(username, password);

    if (success) {

        Session.setUser(username); // save admin session

        Stage stage =
            (Stage) root.getScene().getWindow();

        SceneSwitcher.switchScene(
                stage,
                "/ui/AdminDashboard.fxml"
        );

    } else {
        loginError.setText("Invalid admin credentials");
        loginError.setVisible(true);
    }
}

    //  BACK BUTTON
    @FXML
    private void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/ui/Welcome.fxml");
    }
}
