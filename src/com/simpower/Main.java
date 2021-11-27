package com.simpower;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();

        stage.getIcons().add(new Image("file:assets/com/logo.png"));
        stage.setTitle("Sim Power | UTBM - AP4B - Autumn 2021");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}