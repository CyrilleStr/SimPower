package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.Game;
import com.simpower.models.grid.Grid;
import com.simpower.models.time.Clock;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.action.Action;

public class GameController {
    private Grid grid;
    private Game game;
    private Clock clock;
    private boolean isClockTicking;
    private ImageView pauseImgView;
    private ImageView playImgView;
    @FXML private Button quitGameBtn;
    @FXML private Text info;
    @FXML private GridPane gridContainer;
    @FXML private Label clockLabel;
    @FXML private Button pauseGameBtn;
    /* Instance a new game controller*/
    public GameController(){}

    /* This function is called once all the controller associated FXML contents have been fully loaded */
    @FXML
    public void initialize(){
        this.grid = new Grid(gridContainer);
        this.game = new Game();
        this.clock = new Clock(clockLabel);
        this.clock.start();
        this.isClockTicking = true;
        this.pauseImgView = new ImageView(new Image("file:src/main/resources/com/simpower/assets/textures/hotbar/pause.png"));
        this.playImgView = new ImageView(new Image("file:src/main/resources/com/simpower/assets/textures/hotbar/play.png"));
        this.playImgView.setFitHeight(25);
        this.playImgView.setFitWidth(25);
        this.pauseImgView.setFitWidth(25);
        this.pauseImgView.setFitHeight(25);
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

    /**
     * Play/pause the timer on user action while changing the pause btn image
     *
     * @param event
     * @throws InterruptedException
     */
    @FXML
    void pauseGameAction(ActionEvent event) throws InterruptedException {
        if(this.isClockTicking){
            this.pauseGameBtn.setGraphic(this.playImgView);
            // Deprecated method but used in lesson
            this.clock.suspend();
            this.isClockTicking = false;
        }else{
            this.pauseGameBtn.setGraphic(this.pauseImgView);
            // Deprecated method but used in lesson
            this.clock.resume();
            this.isClockTicking = true;
        }
    }
}
