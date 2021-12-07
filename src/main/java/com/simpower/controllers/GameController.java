package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.Game;
import com.simpower.models.map.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class GameController {

    private Map map;
    private Game game;
    @FXML private Button quitGameBtn;
    @FXML private Text info;
    @FXML private GridPane mapContainer;
    
    /* Instance a new game controller*/
    public GameController(){
        this.map = new Map();
        this.game = new Game();
    }

    /* Instance a saved game controller*/
    public GameController(String params){
        // TODO controller with saved game
        // this.game = new Game(params[0])
        // this.map = new Map(params[1])
    }

    @FXML
    void quitGame(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main_menu.fxml"));
        quitGameBtn.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    void generateMap(){
        this.map.generateMap(this.mapContainer);
    }
}
