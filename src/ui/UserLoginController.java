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
import javafx.scene.layout.VBox;

public class UserLoginController {

    // ===== Root =====
    @FXML private AnchorPane root;

    //  Fields 
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;

    // Errors 
    @FXML private Label loginError;
    @FXML private Label passwordError;

    
    @FXML private Button loginBtn;

    // INITIALIZE
    @FXML
    public void initialize() {

        
        Platform.runLater(() -> root.requestFocus());

        hideErrors();
        loginBtn.setDisable(true);

        loginField.textProperty().addListener((a,b,c) -> validateForm());
        passwordField.textProperty().addListener((a,b,c) -> validateForm());
    }

    // LOGIN 
@FXML
void handleLogin(ActionEvent event) {

    try {
        validateForm();

        String username = loginField.getText();
        String password = passwordField.getText();

        try (var conn = DB.connect()) {

            String sql =
                "SELECT * FROM users WHERE username=? AND password=?";

            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            var rs = pstmt.executeQuery();

            if (rs.next()) {

                Session.setUser(username);

                Stage stage =
                    (Stage) root.getScene().getWindow();

                SceneSwitcher.switchScene(
                    stage,
                    "/ui/UserDashboard.fxml"
                );

            } else {
                
                throw new InvalidLoginException("Invalid username or password");
            }

        }

    } catch (InvalidLoginException e) {
        showAlert(e.getMessage()); 
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    // VALIDATION 
    private boolean validateForm() {

        hideErrors();
        boolean valid = true;

        if (loginField.getText().isEmpty()) {
            loginError.setText("Username or Email required");
            loginError.setVisible(true);
            valid = false;
        }

        if (passwordField.getText().isEmpty()) {
            passwordError.setText("Password required");
            passwordError.setVisible(true);
            valid = false;
        }

        loginBtn.setDisable(!valid);
        return valid;
    }

    private void hideErrors() {
        loginError.setVisible(false);
        passwordError.setVisible(false);
    }

    //  NAVIGATION
    @FXML
    void goBack(ActionEvent event) throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
    SceneSwitcher.switchScene(stage, "/ui/Welcome.fxml");
    }

    @FXML
    void goToRegister(MouseEvent event) throws Exception {
        Stage stage = (Stage) root.getScene().getWindow();
    SceneSwitcher.switchScene(stage, "/ui/Register.fxml");
    }

    private void loadPage(String fxml) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(page));
        stage.show();
    }
    
    private void showAlert(String msg) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText(null);
    alert.setContentText(msg);

    DialogPane pane = alert.getDialogPane();
    //pane.setStyle("-fx-background-color: #006b80;");
    pane.setStyle(
    "-fx-background-color: #006b80;" +
    "-fx-font-size: 14px;" +
    "-fx-text-fill: white;"
);
    
    // Blur background
    javafx.scene.effect.GaussianBlur blur = new javafx.scene.effect.GaussianBlur(10);
    root.setEffect(blur);

    alert.setOnHidden(e -> root.setEffect(null)); // remove blur after closing
    
    // Fade animation
    pane.setOpacity(0);

    alert.setOnShown(e -> {
        javafx.animation.FadeTransition ft =
            new javafx.animation.FadeTransition(javafx.util.Duration.millis(400), pane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    });
    
    alert.showAndWait();
}
}
