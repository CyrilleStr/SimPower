package com.simpower.models.grid;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public interface GridInfos {
    Map<buildingLayer, Image> buildingLayerImages = new HashMap<>();
    Map<resourceLayer, Image> resourceLayerImages = new HashMap<>();
    Map<topLayer, Image> topLayerImages = new HashMap<>();

    int CELL_WIDTH = 32;
    int CELL_HEIGHT = CELL_WIDTH; // square :)

    int NB_CELLS_WIDTH = 64;
    int NB_CELLS_HEIGHT = 64;

    int roadStartX = NB_CELLS_WIDTH / 2;

    int POLLUTION_PERSISTENCE_DAY = 2;

    enum resourceLayer {
        NONE,
        OIL,
        GAS,
        URANIUM,
        COAL,
        RIVER
    }

    enum buildingLayer {
        NONE,
        DELETE,
        /*Building*/
        HOUSE,
        /*Generators*/
        COAL_PLANT,
        GAS_PLANT,
        OIL_PLANT,
        SOLAR_PLANT,
        URANIUM_PLANT,
        WATER_MILL,
        WIND_FARM,
        /*Mines*/
        COAL_MINE,
        GAS_MINE,
        OIL_MINE,
        URANIUM_MINE,

        /*Roads*/

        /**
         * . . .
         * . o .
         * . . .
         */
        ROAD,

        /**
         * . | .
         * . o .
         * . | .
         */
        ROAD_NORTH_SOUTH,
        ROAD_WEST_EAST,

        /**
         * . | .
         * - o -
         * . | .
         */
        ROAD_NORTH_EAST_SOUTH_WEST,

        /**
         * . | .
         * . o -
         * . . .
         */
        ROAD_NORTH_EAST,
        ROAD_EAST_SOUTH,
        ROAD_SOUTH_WEST,
        ROAD_WEST_NORTH,

        /**
         * . | .
         * . o -
         * . | .
         */
        ROAD_NORTH_EAST_SOUTH,
        ROAD_EAST_SOUTH_WEST,
        ROAD_SOUTH_WEST_NORTH,
        ROAD_WEST_NORTH_EAST,

        /**
         * . | .
         * . o .
         * . . .
         */
        ROAD_NORTH,
        ROAD_EAST,
        ROAD_SOUTH,
        ROAD_WEST
    }

    enum topLayer{
        NONE,
        GRASS,
        RIVER,
        ICE,
        SNOW
    }

    enum resourceStock {
        GAS,
        OIL,
        URANIUM,
        COAL,
        NONE
    }
}
