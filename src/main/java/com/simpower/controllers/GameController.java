package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.Game;
import com.simpower.models.grid.Grid;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameController {
    private Grid grid;
    private Game game;
    @FXML private Button quitGameBtn;
    @FXML private Text info;
    @FXML private GridPane gridContainer;
    @FXML private Label infoLabel;
    
    /* Instance a new game controller*/
    public GameController(){}

    /* This function is called once all the controller associated FXML contents have been fully loaded */
    @FXML
    public void initialize(){
        this.grid = new Grid(gridContainer, infoLabel);
        this.game = new Game();
    }

    /* Instance a saved game controller*/
    public GameController(String params){
        // TODO controller with saved game
        // this.game = new Game(params[0])
        // this.grid = new Grid(params[1])
    }

    @FXML
    void quitGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main.fxml"));
        quitGameBtn.getScene().setRoot(fxmlLoader.load());
    }
}
