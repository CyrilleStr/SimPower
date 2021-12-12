package com.simpower.models.time;

public interface TimeInfos {
    int sunrise[] = {8,6,8,10};
    int moonrise[] = {20,21,20,18};
    int solstice[] = {80,172,264,355}; // day of the year

    enum Season {
        SPRING,
        SUMMER,
        AUTUMN,
        WINTER
    }

    /*
     * Test if a given value is inside a given array
     */
    default boolean contains(final int[] arr, final int x) {
        for (int a : arr) if (x == a) return true;
        return false;
    }
}