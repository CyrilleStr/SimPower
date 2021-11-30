package com.simpower.view.menu;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import com.simpower.view.menu.PauseMenu;

public class GameMenu extends Parent {
    private VBox menus[] = new VBox[2];

    public GameMenu() {
        for (int i = 0; i < menus.length; i++) {
            menus[i] = new VBox(10);
            menus[i].setTranslateX(100);
            menus[i].setTranslateY(200);
        }

        final int offset = 400;
        menus[1].setTranslateX(offset);

        PauseMenu btnResume = new PauseMenu("RESUME");
        btnResume.setOnMouseClicked(event -> {
            FadeTransition ft = new FadeTransition(Duration.seconds(.5), this);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setOnFinished(evt -> setVisible(false));
            ft.play();
        });

        PauseMenu btnOptions = new PauseMenu("OPTIONS");
        btnOptions.setOnMouseClicked(event -> {
            getChildren().add(menus[1]);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(.25), menus[0]);
            tt.setToX(menus[0].getTranslateX() - offset);

            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(.5), menus[1]);
            tt1.setToX(menus[0].getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menus[0]);
            });
        });

        PauseMenu btnExit = new PauseMenu("EXIT");
        btnExit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        PauseMenu btnBack = new PauseMenu("BACK");
        btnBack.setOnMouseClicked(event -> {
            getChildren().add(menus[0]);

            TranslateTransition tt = new TranslateTransition(Duration.seconds(.25), menus[1]);
            tt.setToX(menus[1].getTranslateX() + offset);

            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(.5), menus[0]);
            tt1.setToX(menus[1].getTranslateX());

            tt.play();
            tt1.play();

            tt.setOnFinished(evt -> {
                getChildren().remove(menus[1]);
            });
        });

        PauseMenu btnSound = new PauseMenu("SOUND");
        PauseMenu btnVideo = new PauseMenu("VIDEO");

        menus[0].getChildren().addAll(btnResume, btnOptions, btnExit);
        menus[1].getChildren().addAll(btnBack, btnSound, btnVideo);

        Rectangle bg = new Rectangle(1080, 720);
        bg.setFill(Color.GREY);
        bg.setOpacity(.4);

        getChildren().addAll(bg, menus[0]);
    }
}
