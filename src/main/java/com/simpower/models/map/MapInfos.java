package com.simpower.models.map;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public interface MapInfos {
    Map<resourceLayer, Image> resourceLayerImages = new HashMap<>();
    Map<topLayer,Image> topLayerImages = new HashMap<>();
    Map<pollutionLayer,Image> pollutionLayerImages = new HashMap<>();

    static final int MAP_WIDTH = 256; // how many slot the map have sideways
    static final int CHUNK_WIDTH = 32; // how many slot a chunk have sideways
    static final int SLOT_SIZE = 8;

    static final int NB_CHUNK_WIDTH = MAP_WIDTH / CHUNK_WIDTH;

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
        ROAD0,
        ROAD1,
        ROAD2,
        ROAD3,
        ROAD4
    }

    enum pollutionLayer {
        NONE,
        EVENLY,
        POLLUTED
    }
}
