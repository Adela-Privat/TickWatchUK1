package TickWatchUK1.TickWatchUK1;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private static App instance;

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        scene = new Scene(loadFxml("HomePage"), 1280, 800);

        URL css = App.class.getResource("/styles.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        stage.setTitle("TickWatch UK");
        stage.setScene(scene);
        stage.show();
    }

    /** Switch screen. Use the file name without .fxml: "HomePage", "map", "learn", "footer". */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFxml(fxml));
    }

    /** Opens a web link or mailto: link in the default app. */
    public static void openUrl(String url) {
        instance.getHostServices().showDocument(url);
    }

    private static Parent loadFxml(String fxml) throws IOException {
        URL url = App.class.getResource("/" + fxml + ".fxml");
        if (url == null) {
            throw new IOException("Cannot find /" + fxml + ".fxml in src/main/resources");
        }
        return new FXMLLoader(url).load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}