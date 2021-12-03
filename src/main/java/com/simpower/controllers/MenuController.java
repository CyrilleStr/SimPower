package com.simpower.controllers;
import com.simpower.Main;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.json.simple.JSONObject;

import java.io.IOException;

public class MenuController {
    private JSONObject lang = new Main().getLang();

    @FXML private Button newGameBtn;
    @FXML private Button loadGameBtn;
    @FXML private Button settingsBtn;
    @FXML private Button quitGameBtn; // unused
    @FXML private Button creditsBtn;
    @FXML private Button cheatsBtn;
    @FXML private Button langBtn;
    @FXML private Button goBackBtn;

    @FXML private Label langLabel;
    @FXML private Label cheatsLabel;

    @FXML
    protected void quitGame(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/main_menu.fxml"));
        goBackBtn.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    protected void newGame(ActionEvent event){
        newGameBtn.setText("cliqué");
    }

    @FXML
    protected void loadGame(ActionEvent event){
        loadGameBtn.setText("cliqué");
    }
    @FXML
    protected void changeLang(ActionEvent event){
        langBtn.setText("cliqué");
    }
    @FXML
    protected void activateCheats(ActionEvent event){
        this.cheatsLabel.setText((String) this.lang.get("menus.labels.langs.fr_FR"));
    }

    @FXML
    protected void openCredits(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/credits_menu.fxml"));
        creditsBtn.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    protected void openSettings(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fxml/menus/settings_menu.fxml"));
        settingsBtn.getScene().setRoot(fxmlLoader.load());
    }
}
