package com.simpower;

import com.simpower.controllers.JsonReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;

public class Main extends Application {
    private Stage stage;

    /**
     * Change the scene in-game
     *
     * @param fxml string containing data to load the scene needed
     * @throws IOException exceptions
     */
    public void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        this.stage.setScene(scene);
    }

    /**
     * Set everything concerning the window
     *
     * @param stage stage to set up
     * @throws IOException exceptions
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        this.changeScene("fxml/menus/main.fxml");
        this.stage.setTitle("Sim Power | UTBM - AP4B - Autumn 2021");
        this.stage.getIcons().add(new Image("file:src/main/resources/com/simpower/assets/logo.png"));
        this.stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}