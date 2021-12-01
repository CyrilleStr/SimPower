package com.simpower.controllers;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class MenuController {

    @FXML private Button newGameBtn;
    @FXML private Button loadGameBtn;
    @FXML private Button settingsBtn;

    @FXML
    protected void newGame(ActionEvent event){
        newGameBtn.setText("cliqué");
    }

    @FXML
    protected void loadGame(ActionEvent event){
        loadGameBtn.setText("cliqué");
    }

    @FXML
    protected void settings(ActionEvent event){
        settingsBtn.setText("cliqué");
    }
}
