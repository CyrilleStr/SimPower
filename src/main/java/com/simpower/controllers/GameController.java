package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.Game;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos.buildingLayer;
import com.simpower.models.time.Clock;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.IOException;

public class GameController {
    private Grid grid;
    private Game game;
    private Clock clock;
    private int clockSpeeds[];
    private int clockSpeedNumber;
    private ImageView pauseImgView;
    private ImageView playImgView;
    private boolean isTabPaneOpen = false;
    private boolean isPauseMenuOpen = false;
    private buildingLayer buildingType = buildingLayer.NONE;

    @FXML private GridPane pauseMenu;
    @FXML private TabPane tabPane;
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
    void onKeyPressed(KeyEvent ke) {
        switch (ke.getCode()) {
            case ESCAPE:
                this.pauseGame();
                break;
            case SPACE: // todo: find why it doesn't work
                this.pauseTime(false);
                break;
            default:
                break;
        }
    }

    @FXML
    void quitGame(ActionEvent event) throws IOException {
        // TODO implement a quitGame button
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main.fxml"));
        changeClockSpeedBtn.getScene().setRoot(fxmlLoader.load()); // getting root scene using element of that scene :)
    }

    @FXML
    void hidePauseMenu(ActionEvent event) throws IOException {
        this.pauseGame();
    }

    private void pauseGame() {
        this.isPauseMenuOpen = !this.isPauseMenuOpen;
        this.pauseMenu.setVisible(this.isPauseMenuOpen);
        this.pauseTime(true);
    }

    private void pauseTime(boolean forcePause) {
        if (this.clock.isTicking() || forcePause) {
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
     * Play/pause the timer on user action while changing the pause btn image
     * @param event
     * @throws InterruptedException
     */
    @FXML
    void pauseGameAction(ActionEvent event) throws InterruptedException {
        this.pauseTime(false);
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

    @FXML
    void setHouseAsBuildingAction() {
        this.grid.setBuildingAction(buildingLayer.HOUSE);
    }

    @FXML
    void setWorkingBuildAsBuildingAction() {
        this.grid.setBuildingAction(buildingLayer.WORKING_BUILDING);
    }

    @FXML
    void setRoadAsBuildingAction(){
        this.grid.setBuildingAction(buildingLayer.ROAD);
    }

    @FXML
    void setDeleteAsBuildingAction(ActionEvent event) {
        this.grid.setBuildingAction(buildingLayer.NONE);
    }

    private void swapTabPane() {
        this.isTabPaneOpen = !this.isTabPaneOpen;
        this.tabPane.setVisible(this.isTabPaneOpen);
    }

    @FXML
    void openTabPane(ActionEvent event) {
        this.swapTabPane();
    }

    @FXML
    void showGridResources(ActionEvent event) {
        this.grid.showResources();
    }
}
