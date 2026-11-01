package TickWatchUK1.TickWatchUK1;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

public class HomeController {

    @FXML private Circle photoCircle;

    @FXML
    public void initialize() {
        // Optional: put hero.jpg in src/main/resources
        InputStream in = getClass().getResourceAsStream("/hero.jpg");
        if (in != null) {
            photoCircle.setFill(new ImagePattern(new Image(in)));
        } else {
            photoCircle.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(0.0, Color.web("#c9cacd")),
                    new Stop(0.45, Color.web("#b9b5a8")),
                    new Stop(1.0, Color.web("#7a6a4a"))));
        }
    }

    @FXML private void onExploreMap() throws IOException       { App.setRoot("map"); }
    @FXML private void onLearn() throws IOException            { App.setRoot("learn"); }
    @FXML private void onAbout() throws IOException            { App.setRoot("footer"); }
    @FXML private void onExploreSightings() throws IOException { App.setRoot("map"); }

    @FXML
    private void onReportSighting() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Report a sighting");
        a.setHeaderText(null);
        a.setContentText("The report form would open here.");
        a.showAndWait();
    }
}