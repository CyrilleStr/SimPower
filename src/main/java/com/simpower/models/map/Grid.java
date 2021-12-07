package com.simpower.models.map;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;


public class Grid implements MapInfos{
    // private double seed;
    // private ResourceAvailable availableResource;
    // private Clock clock;
    // private int citizens;
    private Chunk[][] chunks;

    public Grid(){
        this.generateMapImageView();
    }

    public void generateMap(GridPane mapContainer) {
        this.chunks = new Chunk[NB_CHUNK_WIDTH][NB_CHUNK_WIDTH];

        for (int x = 0; x < NB_CHUNK_WIDTH; x++) {
            for (int y = 0; y < NB_CHUNK_WIDTH; y++) {
                this.chunks[x][y] = new Chunk(x, y);
                this.chunks[x][y].generateSlots(mapContainer);
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

    public void generateMap(GridPane mapContainer) {
        slots = new Slot[MAP_HEIGHT][MAP_WIDTH];
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                slots[i][j] = new Slot(i, j);
                ImageView imgView = new ImageView(this.topLayerImages.get(slots[i][j].getCurrentTopLayer()));
                imgView.setFitWidth(SLOT_WIDTH);
                imgView.setFitHeight(SLOT_HEIGHT);
                imgView.onMouseReleasedProperty().set((event) -> {
                    this.change(event);
                });
                mapContainer.add(imgView,i,j);
            }
        }
    }
    */

    void generateMapImageView(){
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/map/grass.png");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/textures/map/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }
}
