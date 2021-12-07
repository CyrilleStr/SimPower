package com.simpower.models.grid;

public interface GridInfos {
    static final int X_SIZE = 30;
    static final int Y_SIZE = 30;
    static final int HEIGHT_SLOT = 36;
    static final int WIDTH_SLOT = 36;
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

    enum pollutionLayer{
        NONE,
        VERY_POLLUTED,
        NOT_VERY_POLLUTED
    }
}
