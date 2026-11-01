package TickWatchUK1.TickWatchUK1;

import java.io.IOException;

import javafx.fxml.FXML;

public class FooterController {

    // TODO: put the real placement team address here
    private static final String CONTACT_EMAIL = "placement@example.com";

    @FXML
    private void onContact() {
        App.openUrl("mailto:" + CONTACT_EMAIL + "?subject=TickWatch%20UK");
    }

    @FXML
    private void onBack() throws IOException {
        App.setRoot("HomePage");
    }
}