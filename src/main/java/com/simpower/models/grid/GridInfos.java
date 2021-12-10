package com.simpower.models.grid;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public interface GridInfos {
    Map<buildingLayer, Image> buildingLayerImages = new HashMap<>();
    Map<resourceLayer, Image> resourceLayerImages = new HashMap<>();
    Map<pollutionLayer,Image> pollutionLayerImages = new HashMap<>();
    Map<topLayer,Image> topLayerImages = new HashMap<>();

    int CELL_WIDTH = 64;
    int CELL_HEIGHT = CELL_WIDTH; // square :)

    int NB_CELLS_WIDTH = 64;
    int NB_CELLS_HEIGHT = 64;
    int NB_CELLS = NB_CELLS_HEIGHT * NB_CELLS_WIDTH;

    int MAP_WIDTH = NB_CELLS_WIDTH * CELL_WIDTH;
    int MAP_HEIGHT = NB_CELLS_HEIGHT * CELL_HEIGHT;
    int MAP_SURFACE = MAP_WIDTH * MAP_HEIGHT;

    enum resourceLayer {
        NONE,
        OIL,
        GAS,
        URANIUM,
        COAL
    }

    enum buildingLayer {
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
        VERTICAL_ROAD,
        HORIZONTAL_ROAD,
        CROSS_ROAD,
        TURNED_ROAD,
        TRI_ROAD,
        END_ROAD
    }

    enum topLayer{
        NONE,
        RIVER,
    }

    enum pollutionLayer {
        NONE,
        EVENLY,
        POLLUTED
    }
}
