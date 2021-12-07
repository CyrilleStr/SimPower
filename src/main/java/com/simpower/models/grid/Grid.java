package com.simpower.models.grid;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;


public class Grid implements GridInfos {
    //private double seed;
    //private ResourceAvailable availableResource;
    //private Clock clock;
    private Slot[][] slots;
    //private int citizens;
    private Map<resourceLayer,Image> resourceLayerImages = new HashMap<>();
    private Map<topLayer,Image> topLayerImages = new HashMap<>();
    private Map<pollutionLayer,Image> pollutionLayerImages = new HashMap<>();

    public Grid(){
        this.generateLayerImg();
    }

    /*public void setSeed(double seedS){
        this.seed = seedS;
    }

    public int getCitizens() {
        return this.citizens;
    }
    */
    public void generateGrid(GridPane gridContainer) {
        slots = new Slot[Y_SIZE][X_SIZE];
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                slots[i][j] = new Slot(i, j);
                ImageView imgView = new ImageView(this.topLayerImages.get(slots[i][j].getCurrentTopLayer()));
                imgView.setFitWidth(WIDTH_SLOT);
                imgView.setFitHeight(HEIGHT_SLOT);
                imgView.onMouseReleasedProperty().set((event) -> {
                    this.change(event);
                });
                gridContainer.add(imgView,i,j);
            }
        }
    }

    void generateLayerImg(){
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/ground.jpg");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/textures/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }
}
