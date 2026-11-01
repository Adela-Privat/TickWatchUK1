package TickWatchUK1.TickWatchUK1;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class MapController {

    @FXML private Pane mapLayer;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> speciesBox;
    @FXML private ComboBox<String> timeBox;
    @FXML private Label countLabel;
    @FXML private Label ageLabel;
    @FXML private Label reportIdLabel;
    @FXML private Label speciesLabel;
    @FXML private Label latinLabel;
    @FXML private Label locationLabel;
    @FXML private Label dateLabel;

    private static final Color ORANGE = Color.web("#e0623e");
    private static final Color GREEN  = Color.web("#347a5b");
    private static final Color TEAL   = Color.web("#5fa8a0");
    private static final Color YELLOW = Color.web("#e3b84b");

    private static final double OFFSET_X = 114;
    private static final double OFFSET_Y = 250;

    private final Group mapGroup = new Group();
    private final List<Sighting> sightings = new ArrayList<>();
    private final Map<Sighting, StackPane> markers = new LinkedHashMap<>();
    private Sighting selected;
    private double zoom = 1.0;

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.UK);

    private static class Sighting {
        final double x, y;
        final int count;
        final Color color;
        final String species, latin, location, id;
        final LocalDate date;

        Sighting(double x, double y, int count, Color color, String species,
                 String latin, String location, LocalDate date, String id) {
            this.x = x - OFFSET_X;
            this.y = y - OFFSET_Y;
            this.count = count;
            this.color = color;
            this.species = species;
            this.latin = latin;
            this.location = location;
            this.date = date;
            this.id = id;
        }

        long daysAgo() {
            return ChronoUnit.DAYS.between(date, LocalDate.now());
        }
    }

    @FXML
    public void initialize() {
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(mapLayer.widthProperty());
        clip.heightProperty().bind(mapLayer.heightProperty());
        mapLayer.setClip(clip);
        mapLayer.getChildren().add(mapGroup);

        mapGroup.getChildren().add(buildUk());
        loadSampleData();
        buildMarkers();

        speciesBox.setItems(FXCollections.observableArrayList(
                "All species", "Marsh tick", "Sheep tick", "Hedgehog tick", "Fox tick"));
        speciesBox.getSelectionModel().selectFirst();

        timeBox.setItems(FXCollections.observableArrayList(
                "All time", "Last 30 days", "Last year", "Over a year ago"));
        timeBox.getSelectionModel().selectFirst();

        searchField.textProperty().addListener((o, a, b) -> applyFilters());
        speciesBox.valueProperty().addListener((o, a, b) -> applyFilters());
        timeBox.valueProperty().addListener((o, a, b) -> applyFilters());

        select(sightings.get(3));
        applyFilters();
    }

    private Polygon buildUk() {
        double[] pts = {
            500,272, 512,295, 510,325, 527,345, 527,367, 546,385, 532,415, 550,444,
            537,472, 562,502, 559,530, 595,555, 580,590, 602,611, 590,642, 556,636,
            530,653, 495,650, 467,674, 426,681, 395,664, 407,627, 439,612, 432,583,
            440,529, 455,495, 443,465, 460,440, 461,410, 481,384, 476,357, 492,327, 486,300
        };
        Polygon uk = new Polygon();
        for (int i = 0; i < pts.length; i += 2) {
            uk.getPoints().addAll(pts[i] - OFFSET_X, pts[i + 1] - OFFSET_Y);
        }
        uk.getStyleClass().add("uk-shape");
        return uk;
    }

    private void loadSampleData() {
        add(370, 380, 78, TEAL,   "Sheep tick",    "Ixodes ricinus",      "Glasgow",       2024, 5, 14, "A81F2B");
        add(438, 370, 73, GREEN,  "Sheep tick",    "Ixodes ricinus",      "Edinburgh",     2023, 9, 2,  "B73C9D");
        add(505, 423, 57, ORANGE, "Hedgehog tick", "Ixodes hexagonus",    "Newcastle",     2025, 6, 21, "C11E5A");
        add(483, 474, 59, ORANGE, "Marsh tick",    "Ixodes apronophorus", "Manchester",    2022, 8, 1,  "K4C1DA");
        add(491, 485, 41, ORANGE, "Sheep tick",    "Ixodes ricinus",      "Leeds",         2026, 4, 9,  "D90E41");
        add(363, 490, 73, YELLOW, "Fox tick",      "Ixodes canisuga",     "Llandudno",     2021, 7, 30, "E52A77");
        add(405, 481, 72, GREEN,  "Sheep tick",    "Ixodes ricinus",      "Chester",       2026, 8, 17, "F03B12");
        add(453, 542, 82, YELLOW, "Fox tick",      "Ixodes canisuga",     "Birmingham",    2025, 10, 3, "G77H20");
        add(618, 547, 69, TEAL,   "Sheep tick",    "Ixodes ricinus",      "Norwich",       2024, 7, 11, "H12J88");
        add(604, 566, 61, TEAL,   "Hedgehog tick", "Ixodes hexagonus",    "Ipswich",       2023, 5, 25, "J34K19");
        add(340, 585, 71, GREEN,  "Marsh tick",    "Ixodes apronophorus", "Pembrokeshire", 2022, 6, 6,  "L56M70");
        add(400, 595, 77, YELLOW, "Fox tick",      "Ixodes canisuga",     "Cardiff",       2026, 10, 12, "M78N41");
        add(573, 605, 76, ORANGE, "Sheep tick",    "Ixodes ricinus",      "London",        2026, 9, 28, "N90P63");
        add(483, 642, 57, GREEN,  "Marsh tick",    "Ixodes apronophorus", "Exeter",        2025, 3, 8,  "P12Q85");
    }

    private void add(double x, double y, int count, Color c, String sp, String latin,
                     String loc, int yr, int m, int d, String id) {
        sightings.add(new Sighting(x, y, count, c, sp, latin, loc, LocalDate.of(yr, m, d), id));
    }

    private void buildMarkers() {
        for (Sighting s : sightings) {
            Circle dot = new Circle(15, s.color);
            dot.setStroke(Color.web("#f4f2ea"));
            dot.setStrokeWidth(3);

            Label num = new Label(String.valueOf(s.count));
            num.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 11px;");
            num.setRotate(-35);

            StackPane marker = new StackPane(dot, num);
            marker.setLayoutX(s.x - 15);
            marker.setLayoutY(s.y - 15);
            marker.setCursor(Cursor.HAND);
            marker.setEffect(new DropShadow(10, 0, 3, Color.rgb(0, 0, 0, 0.25)));
            marker.setOnMouseClicked(e -> select(s));

            markers.put(s, marker);
            mapGroup.getChildren().add(marker);
        }
    }

    private void select(Sighting s) {
        if (selected != null) {
            StackPane old = markers.get(selected);
            old.setScaleX(1);
            old.setScaleY(1);
        }
        selected = s;
        StackPane m = markers.get(s);
        m.setScaleX(1.3);
        m.setScaleY(1.3);
        m.toFront();

        ageLabel.setText(s.daysAgo() + " DAYS AGO");
        reportIdLabel.setText("REPORT #" + s.id);
        speciesLabel.setText(s.species);
        latinLabel.setText(s.latin);
        locationLabel.setText(s.location);
        dateLabel.setText(s.date.format(DATE_FMT));
    }

    private void applyFilters() {
        String text = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        String species = speciesBox.getValue();
        String time = timeBox.getValue();
        int total = 0;

        for (Map.Entry<Sighting, StackPane> e : markers.entrySet()) {
            Sighting s = e.getKey();
            boolean ok = true;

            if (!text.isEmpty()) {
                ok = s.location.toLowerCase().contains(text)
                  || s.species.toLowerCase().contains(text)
                  || s.latin.toLowerCase().contains(text);
            }
            if (ok && species != null && !species.equals("All species")) {
                ok = s.species.equals(species);
            }
            if (ok && time != null) {
                long days = s.daysAgo();
                switch (time) {
                    case "Last 30 days":    ok = days <= 30;  break;
                    case "Last year":       ok = days <= 365; break;
                    case "Over a year ago": ok = days > 365;  break;
                    default: break;
                }
            }

            e.getValue().setVisible(ok);
            e.getValue().setManaged(ok);
            if (ok) total += s.count;
        }
        countLabel.setText(String.format(Locale.UK, "%,d", total));
    }

    @FXML private void onZoomIn()  { setZoom(zoom + 0.2); }
    @FXML private void onZoomOut() { setZoom(zoom - 0.2); }

    private void setZoom(double z) {
        zoom = Math.max(0.6, Math.min(2.0, z));
        mapGroup.setScaleX(zoom);
        mapGroup.setScaleY(zoom);
    }

    @FXML
    private void onDirections() {
        String q = URLEncoder.encode(selected.location + ", UK", StandardCharsets.UTF_8);
        App.openUrl("https://www.google.com/maps/search/?api=1&query=" + q);
    }

    @FXML
    private void onShare() {
        String text = selected.species + " reported in " + selected.location
                + " on " + selected.date.format(DATE_FMT) + " (report #" + selected.id + ")";
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard.getSystemClipboard().setContent(content);

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Share");
        a.setHeaderText(null);
        a.setContentText("Copied to clipboard:\n" + text);
        a.showAndWait();
    }

    @FXML
    private void onBack() throws IOException {
        App.setRoot("HomePage");
    }
}