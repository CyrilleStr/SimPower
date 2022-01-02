package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.Game;
import com.simpower.models.grid.Cell;
import com.simpower.models.grid.Grid;
import com.simpower.models.grid.GridInfos.buildingLayer;
import com.simpower.models.grid.buildings.Building;
import com.simpower.models.grid.buildings.House;
import com.simpower.models.grid.buildings.Road;
import com.simpower.models.grid.buildings.mines.CoalMine;
import com.simpower.models.grid.buildings.mines.GasMine;
import com.simpower.models.grid.buildings.mines.OilMine;
import com.simpower.models.grid.buildings.mines.UraniumMine;
import com.simpower.models.grid.buildings.plants.*;
import com.simpower.models.time.Clock;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;
import java.io.*;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

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
    private Map<String, Building> stringToBuildingMap = new HashMap<>();
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
    @FXML private Label errorLabel;
    @FXML private Label electricityLabel;

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
        this.grid = new Grid(gridContainer, infoLabel, buildingType, errorLabel, this);

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
        this.refreshHotBar();
    }

    public void mouseClickedAction(Cell cell){
        if(this.grid.getBuildingLayerAction() != buildingLayer.NONE) {
            String errorMsg = null;
            if (this.game.getMoney() >= this.grid.getBuildingObjectAction().getBuildingCost()) {
                errorMsg = this.grid.mouseClicked(cell);
                if (errorMsg == null) {
                    this.game.setMoney(this.game.getMoney() - this.grid.getBuildingObjectAction().getBuildingCost());
                    this.refreshHotBar();
                }
            } else {
                errorMsg = "You don't have enough money !";
            }

            if (errorMsg != null)
                this.showErrorMessage(errorMsg);
        }
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
        this.eventLoop.stop();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main.fxml"));
        changeClockSpeedBtn.getScene().setRoot(fxmlLoader.load()); // getting root scene using element of that scene :)
    }

    @FXML
    void hidePauseMenu() throws IOException {
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
     * @throws InterruptedException exceptions
     */
    @FXML
    void pauseGameAction() throws InterruptedException {
        this.pauseTime(false);
    }

    /**
     * Change the clock speed on user action
     */
    @FXML
    void changeClockSpeedAction() {
        this.clock.nextSpeed();
        this.changeClockSpeedBtn.setText("✕" + this.clock.getSpeed());
    }

    /**
     * Call the grid method to set a given building (the building layer type is stored in the id)
     *
     * @param event click of the mouse
     */
    @FXML
    void setBuildingAction(MouseEvent event) {
        String id = ((ImageView) event.getSource()).getId();
        this.grid.setBuildingLayerAction(this.stringToBuildingLayerMap.get(id));
        this.grid.setBuildingObjectAction(this.stringToBuildingMap.get(id));
    }

    /**
     * Call the grid to delete a given building
     *
     */
    @FXML
    void deleteBuildingAction(ActionEvent event) {
        this.grid.setBuildingLayerAction(buildingLayer.DELETE);
    }

    private void swapTabPane() {
        this.isTabPaneOpen = !this.isTabPaneOpen;
        this.tabPane.setVisible(this.isTabPaneOpen);
    }

    @FXML
    void openTabPane() {
        this.swapTabPane();
    }

    @FXML
    void showGridResources() {
        this.grid.showResources();
    }

    void loadData(){
        this.stringToBuildingLayerMap.put("roadBtn",buildingLayer.ROAD);
        this.stringToBuildingLayerMap.put("houseBtn",buildingLayer.HOUSE);
        this.stringToBuildingLayerMap.put("CoalMineBtn", buildingLayer.COAL_MINE);
        this.stringToBuildingLayerMap.put("OilMineBtn", buildingLayer.OIL_MINE);
        this.stringToBuildingLayerMap.put("GasMineBtn", buildingLayer.GAS_MINE);
        this.stringToBuildingLayerMap.put("UraniumMineBtn", buildingLayer.URANIUM_MINE);
        this.stringToBuildingLayerMap.put("CoalPlantBtn", buildingLayer.COAL_PLANT);
        this.stringToBuildingLayerMap.put("GasPlantBtn", buildingLayer.GAS_PLANT);
        this.stringToBuildingLayerMap.put("OilPlantBtn", buildingLayer.OIL_PLANT);
        this.stringToBuildingLayerMap.put("UraniumPlantBtn", buildingLayer.URANIUM_PLANT);
        this.stringToBuildingLayerMap.put("WindPlantBtn", buildingLayer.WIND_FARM);
        this.stringToBuildingLayerMap.put("WaterPlantBtn", buildingLayer.WATER_MILL);
        this.stringToBuildingLayerMap.put("SolarPlantBtn", buildingLayer.SOLAR_PLANT);

        /* Link building layer to building object */
        stringToBuildingMap.put("roadBtn", new Road());
        stringToBuildingMap.put("houseBtn", new House());
        stringToBuildingMap.put("UraniumPlantBtn", new NuclearPlant());
        stringToBuildingMap.put("GasPlantBtn", new GasPlant());
        stringToBuildingMap.put("OilPlantBtn", new OilPlant());
        stringToBuildingMap.put("CoalPlantBtn", new CoalPlant());
        stringToBuildingMap.put("SolarPlantBtn", new SolarPlant());
        stringToBuildingMap.put("WindPlantBtn", new WindFarm());
        stringToBuildingMap.put("WaterPlantBtn", new WaterMill());
        stringToBuildingMap.put("CoalMineBtn", new CoalMine());
        stringToBuildingMap.put("GasMineBtn", new GasMine());
        stringToBuildingMap.put("OilMineBtn", new OilMine());
        stringToBuildingMap.put("UraniumMineBtn", new UraniumMine());
    }

    public void refreshHotBar(){
        this.coalLabel.setText(this.game.getCoalStock() + " T");
        this.gazLabel.setText(this.game.getGasStock() + " L");
        this.oilLabel.setText(this.game.getOilStock() + " L");
        this.uraniumLabel.setText(this.game.getUraniumStock() + " T");
        this.moneyLabel.setText(this.game.getMoney() + " €");
        this.electricityLabel.setText(this.game.getElectricityStock() + " W");
    }

    private void showErrorMessage(String message) {
        this.errorLabel.setVisible(true);
        this.errorLabel.setText(message);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> this.errorLabel.setVisible(false));
        pause.play();
    }

    @Override
    public void run() {

        try {
            music();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int day = this.clock.getDayCount();
        while(true){
            try {
                if(this.clock.isTicking()){
                    if(this.clock.getDayCount() <= 1)
                        day = 1;
                    if(day < this.clock.getDayCount()){
                        this.game.eachDay();
                        Platform.runLater(() -> {
                            this.refreshHotBar();
                            this.grid.refreshLayers();
                        });
                        day++;
                    }
                }
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Plays the background music
     * @throws Exception Get clip not going well
     */
    public void music() throws Exception{

        Clip clip = AudioSystem.getClip();
        AudioInputStream ais = AudioSystem.getAudioInputStream(new File("music.wav"));
        clip.open(ais);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-30.0f); //reduce volume by 30 dB
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
