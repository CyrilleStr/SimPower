package com.simpower.models.grid;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;


public interface GridInfos {
    Map<resourceLayer, Image> resourceLayerImages = new HashMap<>();
    Map<topLayer,Image> topLayerImages = new HashMap<>();
    Map<pollutionLayer,Image> pollutionLayerImages = new HashMap<>();

    int SLOT_WIDTH = 16;
    int SLOT_HEIGHT = SLOT_WIDTH; // square :)

    int NB_SLOTS_WIDTH = 64;
    int NB_SLOTS_HEIGHT = 64;
    int NB_SLOTS = NB_SLOTS_HEIGHT * NB_SLOTS_WIDTH;

    int MAP_WIDTH = NB_SLOTS_WIDTH * SLOT_WIDTH;
    int MAP_HEIGHT = NB_SLOTS_HEIGHT * SLOT_HEIGHT;
    int MAP_SURFACE = MAP_WIDTH * MAP_HEIGHT;

    int X_SIZE = 30;
    int Y_SIZE = 30;
    int HEIGHT_SLOT = 36;
    int WIDTH_SLOT = 36;
    
    enum resourceLayer {
        NONE,
        OIL,
        GAS,
        URANIUM,
        COAL
    }

    enum topLayer{
        NONE,
        /*Building*/
        HOUSE,
        WORKING_BUILDING,
        /*Generators*/
        COAL_GENERATOR,
        GAS_GENERATOR,
        OIL_GENERATOR,
        SOLAR_GENERATOR,
        URANIUM_GENERATOR,
        WATER_GENERATOR,
        WIND_GENERATOR,
        /*Mines*/
        COAL_MINE,
        GAS_MINE,
        OIL_MINE,
        URANIUM_MINE,
        /*Roads*/
        VERTICAL_ROAD,
        HORIZONTAL_ROAD,
        CROSS_ROAD,
        TURNED_ROAD
    }

    enum pollutionLayer {
        NONE,
        EVENLY,
        POLLUTED
    }
}
