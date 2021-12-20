package com.simpower.models.grid.buildings;

import com.simpower.models.grid.GridInfos;

public class Road extends Building{

    public Road() {
        super(0, 500, false, true, GridInfos.resourceStock.NONE, false, false);
    }

}
