package com.simpower.models.map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

public class Chunk implements MapInfos {
    private Slot[][] slots;
    private int pos_x = 0;
    private int pos_y = 0;

    public Chunk(int x, int y) {
        this.pos_x = x;
        this.pos_y = y;
    }

    public void generateSlots(GridPane map) {
        this.slots = new Slot[CHUNK_WIDTH][CHUNK_WIDTH];

        for (int x = 0; x < CHUNK_WIDTH; x++) {
            for (int y = 0; y < CHUNK_WIDTH; y++) {
                this.slots[x][y] = new Slot(x, y);

                ImageView texture = new ImageView(this.topLayerImages.get(slots[x][y].getCurrentTopLayer()));
                texture.setFitHeight(SLOT_SIZE);
                texture.setFitWidth(SLOT_SIZE);
                map.add(texture, x, y);
            }
        }
    }

    public int getPos_x() { return this.pos_x; }
    public int getPos_y() { return this.pos_y; }
    public Slot[][] getSlots() { return this.slots; }
}
