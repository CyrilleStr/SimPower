package com.simpower.view.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Menu extends StackPane {
    private String currentMenu;
    private VBox box = new VBox(5);
    private Button[] buttons = new Button[4];

    public Button[] getButtons() {
        return this.buttons;
    }

    public Button getButton(int index) {
        return this.buttons[index];
    }

    public void setButtonsText(int index, String text) {
        this.getButton(index).setText(text);
    }

    public Menu() {
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
        this.getChildren().add(box);
        this.setAlignment(box, Pos.CENTER);
    }
}
