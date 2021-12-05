package com.simpower.models.map;

import com.simpower.controllers.GameController;
import com.simpower.models.time.Clock;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;


public class Map {
    //private double seed;
    //private ResourceAvailable availableResource;
    //private Clock clock;
    private Slot[] slots;
    //private int citizens;

    public Map(){

    }

    /*public void setSeed(double seedS){
        this.seed = seedS;
    }

    public int getCitizens() {
        return this.citizens;
    }
    */
    public void generateMap(GridPane mapContainer){
        Image resource = new Image("file:src/main/resources/com/simpower/assets/oil.png");
        for(int i =0;i<18;i++){
            for(int j=0;j<18;j++){
                ImageView tmp = new ImageView(resource);
                tmp.onMouseReleasedProperty().set((event) -> {
                    this.change(event);
                });
                VBox box = new VBox(tmp);
                box.setFillWidth(true);
                box.prefHeight(10);
                box.prefHeight(10);
                mapContainer.add(box,i,j);
            }
        }
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }
}
