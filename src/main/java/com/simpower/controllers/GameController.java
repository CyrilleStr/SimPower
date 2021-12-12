package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.Game;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos;
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
import javafx.scene.input.MouseEvent;

public class GameController implements GridInfos {
    private Grid grid;
    private Game game;
    private Clock clock;
    private int clockSpeeds[];
    private int clockSpeedNumber;
    private ImageView pauseImgView;
    private ImageView playImgView;
    private buildingLayer buildingType = buildingLayer.NONE;

    @FXML private Button quitGameBtn;
    @FXML private Text info;
    @FXML private GridPane gridContainer;
    @FXML private Label clockLabel;
    @FXML private Label infoLabel;
    @FXML private Button pauseGameBtn;
    @FXML private Button changeClockSpeedBtn;

    /**
     * Instance a new game controller
     */
    public GameController(){}

    /**
     * Instance a saved game controller
     *
     * @param params
     */
    public GameController(String params){
        // TODO controller with saved game
        // this.game = new Game(params[0])
        // this.grid = new Grid(params[1])
    }

    /**
     * This function is called once all the controller associated FXML contents have been fully loaded
     */
    @FXML
    public void initialize(){
        this.grid = new Grid(gridContainer, infoLabel, buildingType);

        this.clock = new Clock(grid, clockLabel);
        this.clock.start();
        this.clockSpeeds = new int[]{1,4,7,10,100};
        this.clockSpeedNumber = 0;
        this.pauseImgView = new ImageView(new Image("file:src/main/resources/com/simpower/assets/textures/hotbar/pause.png"));
        this.playImgView = new ImageView(new Image("file:src/main/resources/com/simpower/assets/textures/hotbar/play.png"));
        this.playImgView.setFitHeight(25);
        this.playImgView.setFitWidth(25);
        this.pauseImgView.setFitWidth(25);
        this.pauseImgView.setFitHeight(25);

        this.game = new Game(grid, clock);
    }

    @FXML
    void quitGame(ActionEvent event) throws IOException {
        // TODO implement a quitGame button
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main.fxml"));
        quitGameBtn.getScene().setRoot(fxmlLoader.load());
    }

    /* Clock */

    /**
     * Play/pause the timer on user action while changing the pause btn image
     * @param event
     * @throws InterruptedException
     */
    @FXML
    void pauseGameAction(ActionEvent event) throws InterruptedException {
        if (this.clock.isTicking()) {
            this.pauseGameBtn.setGraphic(this.playImgView);
            // Deprecated method but used in lesson
            this.clock.suspend();
            this.clock.setTicking(false);
        }
        else {
            this.pauseGameBtn.setGraphic(this.pauseImgView);
            // Deprecated method but used in lesson
            this.clock.resume();
            this.clock.setTicking(true);
        }
    }

    /**
     * Change the clock speed on user action (x1, x2, x5, x10)
     * @param event
     */
    @FXML
    void changeClockSpeedAction(ActionEvent event) {
        this.clockSpeedNumber++;
        if (this.clockSpeedNumber > this.clockSpeeds.length - 1) this.clockSpeedNumber = 0;
        this.changeClockSpeedBtn.setText("x"+this.clockSpeeds[this.clockSpeedNumber]);
        this.clock.setSpeed(this.clockSpeeds[this.clockSpeedNumber]);
    }

    /* Implement drag and drop */
    //SourceField

    @FXML
    void onMousePressedHandler(MouseEvent event) {
        event.setDragDetect(true);
    }

    @FXML
    void onMouseReleasedHandler(MouseEvent event) {
        ((ImageView) event.getSource()).setMouseTransparent(false);
    }

    @FXML
    void onMouseDraggedHandler(MouseEvent event) {
        event.setDragDetect(false);
    }

    @FXML
    void onDragDetectedHandler(MouseEvent event) {
        ((ImageView) event.getSource()).startFullDrag();
        this.grid.setBuildingToDrop(((ImageView) event.getSource()).getId());
    }

    @FXML
    void addRoadOnAction(ActionEvent event){
        this.grid.setBuildingType(buildingLayer.ROAD);
        // Cell test = new Cell(GridInfos.NB_CELLS_WIDTH/2, 1);
        // this.grid.roadBuilder(test);
    }
}
