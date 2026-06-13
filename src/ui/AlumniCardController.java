package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AlumniCardController {

    @FXML private ImageView alumniImage;
    @FXML private Label nameLabel;
    @FXML private Label educationLabel;
    @FXML private Label positionLabel;
    @FXML private Label emailLabel;
    @FXML private Label researchLabel;
    @FXML private Label jobsLabel;
    @FXML private Label summaryLabel;

    public void setData(Alumni alumni) {

        nameLabel.setText("👤 " + alumni.getName());
        educationLabel.setText("🎓 " + alumni.getEducation());
        positionLabel.setText("💼 " + alumni.getPosition());
        emailLabel.setText("📧 " + alumni.getEmail());
        researchLabel.setText("🔬 " + alumni.getResearch());
        jobsLabel.setText("💼 Previous: " + alumni.getJobs());
        summaryLabel.setText("⭐ " + alumni.getSummary());

        try {
    String path = alumni.getImagePath();

    Image img = new Image(
        getClass().getResourceAsStream(path)
    );

    alumniImage.setImage(img);

} catch (Exception e) {
    System.out.println("Image not found: " + alumni.getImagePath());
}
    }
}