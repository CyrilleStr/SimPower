package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.Game;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos.buildingLayer;
import com.simpower.models.time.Clock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseEvent;

import static java.lang.Thread.sleep;

public class GameController implements Runnable{
    private Grid grid;
    private Game game;
    private Clock clock;
    private ImageView pauseImgView;
    private ImageView playImgView;
    private boolean isTabPaneOpen = false;
    private boolean isPauseMenuOpen = false;
    private Map<String, buildingLayer> stringToBuildingLayerMap = new HashMap<>();
    private buildingLayer buildingType = buildingLayer.NONE;
    private Thread eventLoop;

    @FXML private GridPane pauseMenu;
    @FXML private TabPane tabPane;
    @FXML private GridPane gridContainer;
    @FXML private Label clockLabel;
    @FXML private Label infoLabel;
    @FXML private Button pauseGameBtn;
    @FXML private Button changeClockSpeedBtn;
    @FXML private Label moneyLabel;
    @FXML private Label oilLabel;
    @FXML private Label uraniumLabel;
    @FXML private Label coalLabel;
    @FXML private Label gazLabel;

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
        this.loadData();
        this.grid = new Grid(gridContainer, infoLabel, buildingType);

        this.clock = new Clock(grid, clockLabel);
        this.clock.start();

        this.pauseImgView = new ImageView(new Image("file:src/main/resources/com/simpower/assets/textures/hotbar/pause.png"));
        this.playImgView = new ImageView(new Image("file:src/main/resources/com/simpower/assets/textures/hotbar/play.png"));

        this.playImgView.setFitHeight(25);
        this.playImgView.setFitWidth(25);
        this.pauseImgView.setFitWidth(25);
        this.pauseImgView.setFitHeight(25);

        this.game = new Game(grid, clock);
        this.eventLoop = new Thread(this);
        this.eventLoop.start();
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

        this.clock.stop();

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
     * Change the clock speed on user action
     * @param event
     */
    @FXML
    void changeClockSpeedAction(ActionEvent event) {
        this.clock.nextSpeed();
        this.changeClockSpeedBtn.setText("✕" + this.clock.getSpeed());
    }

    /**
     * Call the grid method to set a given building (the building layer type is store in the id)
     *
     * @param event
     */
    @FXML
    void setBuildingAction(MouseEvent event) {
        this.grid.setBuilding(this.stringToBuildingLayerMap.get(((ImageView) event.getSource()).getId()));
    }

    /**
     * Call the grid to delete a given building
     *
     * @param event
     */
    @FXML
    void deleteBuildingAction(ActionEvent event) {
        this.grid.setBuilding(buildingLayer.NONE);
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

    void loadData(){
        this.stringToBuildingLayerMap.put("roadBtn",buildingLayer.ROAD);
        this.stringToBuildingLayerMap.put("houseBtn",buildingLayer.HOUSE);
        this.stringToBuildingLayerMap.put("workingBuildingBtn",buildingLayer.WORKING_BUILDING);
    }

    @Override
    public void run() {
        while(true){
            try {
                if(this.clock.isTicking()){
                    this.game.eachDay();
                    this.coalLabel.setText(this.game.getCoalStock() + " T");
                    this.gazLabel.setText(this.game.getGasStock() + " L");
                    this.oilLabel.setText(this.game.getOilStock() + " L");
                    this.uraniumLabel.setText(this.game.getUraniumStock() + " T");
                    this.moneyLabel.setText(this.game.getMoney() + " €");
                }
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
