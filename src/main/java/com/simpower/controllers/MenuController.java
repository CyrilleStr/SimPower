package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.DataModel;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MenuController {
    private DataModel dataModel = new DataModel();

    @FXML private Button loadGameBtn;
    @FXML private Button settingsBtn;
    @FXML private Button creditsBtn;
    @FXML private Button goBackBtn;
    @FXML private Slider soundSlider;

    @FXML
    public void initialize() {

        // work around since the fxml property doesn't seem to be fired by default
        if (soundSlider != null) {
            this.soundSlider.valueChangingProperty().addListener((obs, oldVal, newVal) -> {
                this.updateSoundVolume(null);
            });
        }
    }

    /**
     * Leave the program
     *
     * @param event click on the button "Leave game"
     */
    @FXML
    protected void quitGame(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Bring the player back to the main menu
     *
     * @param event click on the button "Main menu"
     * @throws IOException exceptions
     */
    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main.fxml"));
        goBackBtn.getScene().setRoot(fxmlLoader.load());
    }

    /**
     * Create a new game
     *
     * @param event click on the button "New game"
     * @throws IOException exceptions
     */
    @FXML
    protected void newGame(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/game.fxml"));
        settingsBtn.getScene().setRoot(fxmlLoader.load());
    }

    // todo: Add loading screen & loads event for saved games
    @FXML
    protected void loadGame(ActionEvent event){
        loadGameBtn.setText("Not yet Implemented");
    }

    /**
     * Open the credits menu
     *
     * @param event click on the button "Open credits"
     * @throws IOException exceptions
     */
    @FXML
    protected void openCredits(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/credits.fxml"));
        creditsBtn.getScene().setRoot(fxmlLoader.load());
    }

    /**
     * Open the settings menu
     *
     * @param event click on the button "Open settings"
     * @throws IOException exceptions
     */
    @FXML
    protected void openSettings(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/settings.fxml"));
        settingsBtn.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    protected void updateSoundVolume(ActionEvent event) {
        JSONObject test = this.dataModel.read("data/settings.json");
        System.out.println(test);
    }
}
