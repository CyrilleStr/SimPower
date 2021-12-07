package com.simpower.models.map;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class Grid implements MapInfos{
    // private double seed;
    // private ResourceAvailable availableResource;
    // private Clock clock;
    // private int citizens;
    private Slot[][] slots;

    public Grid(){
        this.generateMapImageView();
    }

    public void generateMap(GridPane mapContainer) {
        this.slots = new Slot[NB_SLOTS_WIDTH][NB_SLOTS_HEIGHT];
        for (int x = 0; x < NB_SLOTS_WIDTH; x++) {
            mapContainer.getColumnConstraints().addAll(new ColumnConstraints(SLOT_WIDTH));

            for (int y = 0; y < NB_SLOTS_HEIGHT; y++) {
                mapContainer.getRowConstraints().addAll(new RowConstraints(SLOT_HEIGHT));
                this.slots[x][y] = new Slot(x, y);

                ImageView imgView = new ImageView(this.topLayerImages.get(slots[x][y].getCurrentTopLayer()));
                imgView.setFitWidth(SLOT_WIDTH);
                imgView.setFitHeight(SLOT_HEIGHT);

                imgView.hoverProperty().addListener((observable, oldValue, newValue) -> {
                });

                mapContainer.getColumnConstraints();
                mapContainer.add(imgView, x, y);
            }
        }

    }

    /*
    public void setSeed(double seedS){
        this.seed = seedS;
    }

    public int getCitizens() {
        return this.citizens;
    }
     */

    void generateMapImageView(){
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/map/oil.png");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/textures/map/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }
}
