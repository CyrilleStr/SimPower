package com.simpower;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Main extends Application {
    private Stage stage;

    /**
     * Set everything about JavaFX window
     * @param stage stage to set up
     * @throws IOException when file not found
     */
    public void start (Stage stage) throws IOException {
        this.stage = stage;
        this.stage.setTitle("Sim Power | UTBM - AP4B - Autumn 2021");
        this.stage.getIcons().add(new Image("file:src/main/resources/com/simpower/assets/logo.png"));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        this.stage.setScene(scene);

        this.stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}