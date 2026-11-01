package TickWatchUK1.TickWatchUK1;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class LearnController {

    @FXML private HBox chartBars;
    @FXML private HBox chartMonths;

    // bar heights in pixels, Jan to Dec
    private static final double[] HEIGHTS = { 19, 26, 41, 62, 82, 99, 92, 73, 55, 38, 26, 19 };
    private static final int PEAK = 5;

    @FXML
    public void initialize() {
        for (int i = 0; i < HEIGHTS.length; i++) {
            Region bar = new Region();
            bar.setPrefSize(18, HEIGHTS[i]);
            bar.setMinSize(18, HEIGHTS[i]);
            bar.setMaxSize(18, HEIGHTS[i]);
            bar.getStyleClass().add(i == PEAK ? "bar-peak" : "bar");

            VBox column = new VBox(5);
            column.setAlignment(Pos.BOTTOM_CENTER);
            if (i == PEAK) {
                Label peak = new Label("Peak");
                peak.getStyleClass().add("peak-label");
                column.getChildren().add(peak);
            }
            column.getChildren().add(bar);
            chartBars.getChildren().add(column);
        }

        String[] shown = { "Jan", "Apr", "Jul", "Oct", "Dec" };
        for (int i = 0; i < shown.length; i++) {
            Label l = new Label(shown[i]);
            l.getStyleClass().add("month-label");
            chartMonths.getChildren().add(l);
            if (i < shown.length - 1) {
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                chartMonths.getChildren().add(spacer);
            }
        }
    }

    @FXML
    private void onExploreGuide() {
        info("Identification guide", "The species guide would open here.");
    }

    @FXML
    private void onViewChecks() {
        info("Five checks after every walk",
                "1. Check warm, hidden areas first\n"
              + "2. Brush clothing before going inside\n"
              + "3. Use vet-recommended pet protection\n"
              + "4. Check pets, especially ears, armpits and belly\n"
              + "5. Shower and check again within a few hours");
    }

    @FXML
    private void onBack() throws IOException {
        App.setRoot("HomePage");
    }

    private void info(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}