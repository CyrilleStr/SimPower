package com.simpower.controllers;

import com.simpower.Main;
import com.simpower.models.JSONReader;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MenuController {
    private JSONReader settingsData = new JSONReader();
    private MusicController music = new MusicController();

    @FXML private Button loadGameBtn;
    @FXML private Button settingsBtn;
    @FXML private Button creditsBtn;
    @FXML private Button goBackBtn;
    @FXML private Slider soundSlider;

    @FXML
    public void initialize() {
        // work around since the fxml property doesn't seem to be fired by default
        if (soundSlider != null) {
            JSONObject settings = this.settingsData.read("data/settings.json");
            this.soundSlider.setValue((Double.parseDouble(settings.get("SoundVolume").toString())));

            this.soundSlider.setOnMouseReleased((event) -> {
                this.updateSoundVolume((float) this.soundSlider.getValue());
            });
        }

        this.music.play();
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
        this.music.stop();
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
        this.music.stop();
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
        this.music.stop();
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
        this.music.stop();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/settings.fxml"));
        settingsBtn.getScene().setRoot(fxmlLoader.load());
    }

    public void updateSoundVolume(float value) {
        JSONObject settings = this.settingsData.read("data/settings.json");
        settings.put("SoundVolume", value);
        try {
            this.settingsData.write("data/settings.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.music.setVolume(value);
    }
}
