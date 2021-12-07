package com.simpower.models.map;

import com.simpower.controllers.GameController;
import com.simpower.models.map.buildings.Buildings;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Slot implements MapInfos{
    private int pos_x = 0;
    private int pos_y = 0;
    private boolean empty = true;
    private Buildings building = null;
    private int pollutionLevel = 0;
    private ImageView img;

    public Slot(int x, int y){
        Image groundImg = new Image("file:src/main/resources/com/simpower/assets/ground.png");
        this.pos_x = x;
        this.pos_y = y;
        this.img = new ImageView(groundImg);
        /*img.computeAreaInScreen();*/
        this.img.setFitHeight(HEIGHT_SLOT);
        this.img.setFitWidth(WIDTH_SLOT);
    }

    public int getPos_x() {
        return this.pos_x;
    }

    public int getPos_y() {
        return this.pos_y;
    }

    public ImageView getImg(){ return this.img; }

    public ImageView setImg(ImageView newImg){
        ImageView tmp = this.img;
        this.img = newImg;
        return tmp;
    }

    public int getPollutionLevel() {
        return this.pollutionLevel;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setBuilding(Buildings building) {
        if (this.isEmpty()) this.empty = false;

        this.building = building;
    }
}
