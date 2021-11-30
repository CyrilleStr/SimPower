package com.simpower;

import com.simpower.view.menu.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller extends Application {
    private int displayed = 0;
    private Menu displayedScene = new Menu();

    private launchCredits() {

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(new Image("file:assets/com/logo.png"));
        stage.setTitle("Sim Power | UTBM - AP4B - Autumn 2021");

        for (int i = 0; i < this.displayedScene.getButtons().length; i++) {
            int index = i;
            this.displayedScene.getButton(i).setOnAction(value -> {
                switch (this.displayedScene.getButton(index).getText()) {
                    case "CREDITS":
                        this.launchCredits();
                        break;
                    default:
                        this.displayedScene.setButtonsText(index, "Clicked");
                        break;
                }
            });
        }

        stage.setScene(new Scene(displayedScene, 1080, 720));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch();
    }
}