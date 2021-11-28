package com.simpower;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private StackPane root = new StackPane();
    private VBox box = new VBox(5);
    private Button[] buttons = new Button[4];

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("file:assets/com/logo.png"));
        stage.setTitle("Sim Power | UTBM - AP4B - Autumn 2021");

        buttons[0] = new Button("PLAY");
        buttons[0].setStyle("-fx-font: 22 arrial; -fx-base: #b6e7c9;");
        buttons[0].setPrefSize(300, 50);

        buttons[1] = new Button("SETTINGS");
        buttons[1].setStyle("-fx-font: 22 arrial; -fx-base: #b6e7c9;");
        buttons[1].setPrefSize(300, 50);

        buttons[2] = new Button("CREDITS");
        buttons[2].setStyle("-fx-font: 22 arrial; -fx-base: #b6e7c9;");
        buttons[2].setPrefSize(300, 50);

        buttons[3] = new Button("QUIT GAME");
        buttons[3].setStyle("-fx-font: 22 arrial; -fx-base: #b6e7c9;");
        buttons[3].setPrefSize(300, 50);

        box.getChildren().addAll(buttons);
        root.getChildren().add(box);
        root.setAlignment(box, Pos.CENTER);
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}