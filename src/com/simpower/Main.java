package com.simpower;

import com.simpower.view.menu.GameMenu;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private GameMenu gameMenu;

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("file:assets/com/logo.png"));
        stage.setTitle("Sim Power | UTBM - AP4B - Autumn 2021");

        Pane root = new Pane();
        root.setPrefSize(1080, 720);

        gameMenu = new GameMenu();
        gameMenu.setVisible(true);

        root.getChildren().addAll(gameMenu);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                if (!gameMenu.isVisible()) {
                    FadeTransition ft = new FadeTransition(Duration.seconds(.5), gameMenu);
                    ft.setFromValue(0);
                    ft.setToValue(1);

                    gameMenu.setVisible(true);
                    ft.play();
                }
                else {
                    FadeTransition ft = new FadeTransition(Duration.seconds(.5), gameMenu);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.setOnFinished(evt -> gameMenu.setVisible(false));
                    ft.play();
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}