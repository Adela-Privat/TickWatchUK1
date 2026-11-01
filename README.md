# TickWatch UK

Community-powered tick awareness app built with JavaFX. Explore reported tick sightings across the UK, learn what to look for, and find out how to stay safer outdoors.

> Educational prototype for the Elanco student placement task. Not an official tick surveillance service.

## Features

- **Home page**: hero section with quick links to the map and report form
- **Live Activity Explorer (map)**: clickable sighting markers, search by town or species, species and time filters, zoom controls, and a details panel with Directions and Share buttons
- **Learn & protect**: identification guide, five-checks prevention checklist, and a seasonal activity chart
- **About**: project information and contact link

## Tech stack

- Java 21
- JavaFX 21.0.2 (controls and FXML)
- Maven
- SQLite (sqlite-jdbc)

## Project structure

```
src/main/java/TickWatchUK1/TickWatchUK1/
    App.java                 JavaFX application and screen switching
    Launcher.java            Entry point used to run the app
    HomeController.java      HomePage.fxml
    MapController.java       map.fxml
    LearnController.java     learn.fxml
    FooterController.java    footer.fxml
src/main/resources/
    HomePage.fxml
    map.fxml
    learn.fxml
    footer.fxml
    styles.css
pom.xml
```

## How to run

### In Eclipse

1. Import the project as an existing Maven project.
2. Right-click the project, choose **Maven > Update Project...**, tick **Force Update**, and click OK.
3. Right-click `Launcher.java` and choose **Run As > Java Application**.

### From the terminal

```
mvn clean javafx:run
```

## Requirements

- JDK 21
- Maven (or the Maven support built into Eclipse)

## Data

Sighting data in the map screen is sample data used for the prototype. Replace `loadSampleData()` in `MapController.java` with a call to the real open API when it is available. No personal data is collected.

## Author

Adel Emilbekova
