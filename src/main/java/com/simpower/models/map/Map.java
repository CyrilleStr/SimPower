package com.simpower.models.map;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class Map implements MapInfos{
    //private double seed;
    //private ResourceAvailable availableResource;
    //private Clock clock;
    private Slot[][] slots;
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
        slots = new Slot[Y_SIZE][X_SIZE];
        for(int i = 0; i< Y_SIZE; i++){
            for(int j = 0; j< X_SIZE; j++){
                slots[i][j] = new Slot(i,j);
                slots[i][j].getImg().onMouseReleasedProperty().set((event) -> {
                    this.change(event);
                });
                mapContainer.add(slots[i][j].getImg(),i,j);
            }
        }
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }
}
