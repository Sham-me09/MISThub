package ui;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SceneSwitcher {

    public static void switchScene(Stage stage, String fxml) throws Exception {

        Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(fxml));

        Scene scene = new Scene(root);
        stage.setScene(scene);

        FadeTransition fade = new FadeTransition(Duration.millis(400), root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
