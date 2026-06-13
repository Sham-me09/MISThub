package ui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;


public class WelcomeController {
  
    @FXML
    private void userLogin(ActionEvent event) throws Exception {
     
        Parent root = FXMLLoader.load(getClass().getResource("/ui/UserLogin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        FadeTransition fade = new FadeTransition(Duration.millis(400), root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
       
    }

    @FXML
private void adminLogin(ActionEvent event) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("/ui/AdminLogin.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        FadeTransition fade = new FadeTransition(Duration.millis(400), root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
}
}

