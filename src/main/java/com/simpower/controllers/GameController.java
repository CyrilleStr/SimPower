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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import static java.lang.Thread.sleep;

public class GameController implements Runnable {
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
    private MusicController music = new MusicController();

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
    @FXML private Label happinessLabel;

    /**
     * Instance a new game controller
     */
    public GameController() {}

    /**
     * Instance a saved game controller
     *
     * @param params
     */
    public GameController(String params) {
        // TODO controller with saved game
        // this.game = new Game(params[0])
        // this.grid = new Grid(params[1])
    }

    /**
     * This function is called once all the controller associated FXML contents have
     * been fully loaded
     */
    @FXML
    public void initialize() {
        this.music.play();
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

    /**
     * Get the action done on click depending on where we clicked
     * (If the player can do it depending on game rules)
     *
     * @param cell cell where you do the action
     */
    public void mouseClickedAction(Cell cell) {
        if (this.grid.getBuildingLayerAction() != buildingLayer.NONE) {
            String errorMsg = null;
            if (this.game.getMoney() >= this.grid.getBuildingObjectAction().getBuildingCost()) {
                errorMsg = this.grid.mouseClicked(cell);
                if (errorMsg == null)
                    if (this.grid.getBuildingLayerAction() != buildingLayer.DELETE) {
                        this.game
                                .setMoney(this.game.getMoney() - this.grid.getBuildingObjectAction().getBuildingCost());
                        this.refreshHotBar();
                    }
            } else
                errorMsg = "You don't have enough money !";

            if (errorMsg != null)
                this.showErrorMessage(errorMsg);
        }
    }

    /**
     * Pause the fame thanks to the keyboard
     *
     * @param ke KeyEvent for the key pressed
     */
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

    /**
     * Quit the game thanks to the leave game button
     *
     * @param event mouse click on the button
     * @throws IOException exceptions
     */
    @FXML
    void quitGame(ActionEvent event) throws IOException {
        this.music.stop();

        this.clock.stop();
        this.eventLoop.stop();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main.fxml"));
        changeClockSpeedBtn.getScene().setRoot(fxmlLoader.load()); // getting root scene using element of that scene :)
    }

    /**
     * Hide the pause menu when you want to start playing again
     *
     * @throws IOException exceptions
     */
    @FXML
    void hidePauseMenu() throws IOException {
        this.pauseGame();
    }

    /**
     * Set everything good to pause the game
     */
    private void pauseGame() {
        this.isPauseMenuOpen = !this.isPauseMenuOpen;
        this.pauseMenu.setVisible(this.isPauseMenuOpen);
        this.pauseTime(true);
    }

    /**
     * Pauses the time in-game
     *
     * @param forcePause boolean to determine if we pause or not
     */
    private void pauseTime(boolean forcePause) {
        if (this.clock.isTicking() || forcePause) {
            this.pauseGameBtn.setGraphic(this.playImgView);
            // Deprecated method but used in lesson
            this.clock.suspend();
            this.clock.setTicking(false);
        } else {
            this.pauseGameBtn.setGraphic(this.pauseImgView);
            // Deprecated method but used in lesson
            this.clock.resume();
            this.clock.setTicking(true);
        }
    }

    /**
     * Play/pause the timer on user action while changing the pause btn image
     */
    @FXML
    void pauseGameAction() {
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
     * Call the grid method to set a given building (the building layer type is
     * stored in the id)
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

    /**
     * Open or close the tab pane
     */
    private void swapTabPane() {
        this.isTabPaneOpen = !this.isTabPaneOpen;
        this.tabPane.setVisible(this.isTabPaneOpen);
    }

    /**
     * Open the tab pane
     */
    @FXML
    void openTabPane() {
        this.swapTabPane();
    }

    /**
     * Show the resource grid
     */
    @FXML
    void showGridResources() {
        this.grid.showResources();
    }

    /**
     * Load the data for graphical interface
     */
    void loadData() {
        this.stringToBuildingLayerMap.put("roadBtn", buildingLayer.ROAD);
        this.stringToBuildingLayerMap.put("houseBtn", buildingLayer.HOUSE);
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

    /**
     * Refresh hotbar data
     */
    public void refreshHotBar() {
        this.coalLabel.setText(this.game.getCoalStock() + " T");
        this.gazLabel.setText(this.game.getGasStock() + " L");
        this.oilLabel.setText(this.game.getOilStock() + " L");
        this.uraniumLabel.setText(this.game.getUraniumStock() + " T");
        this.moneyLabel.setText(this.game.getMoney() + " €");
        this.electricityLabel.setText(this.game.getElectricityProduced() + " W");
        this.happinessLabel.setText(this.game.getGlobalhappiness() + "%");
    }

    /**
     * Display error messages
     *
     * @param message error the player has to deal with
     */
    private void showErrorMessage(String message) {
        this.errorLabel.setVisible(true);
        this.errorLabel.setText(message);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> this.errorLabel.setVisible(false));
        pause.play();
    }

    /**
     * Run the game controller
     */
    @Override
    public void run() {
        int day = this.clock.getDayCount();
        while (!this.game.isGameOver()) {
            try {
                if (this.clock.isTicking()) {
                    if (this.clock.getDayCount() <= 1)
                        day = 1;
                    if (day < this.clock.getDayCount()) {
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
        Platform.runLater(() -> {
            this.music.stop();
            this.pauseTime(false);
            this.showErrorMessage("Game Over !");
            PauseTransition pause1 = new PauseTransition(Duration.seconds(2.5));
            PauseTransition pause2 = new PauseTransition(Duration.seconds(2.5));
            pause1.setOnFinished(event -> {
                this.showErrorMessage("All inhabitants left the city.");
                pause2.play();
            });
            pause2.setOnFinished(event -> {
                try {
                    quitGame(new ActionEvent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause1.play();
        });

    }
}
