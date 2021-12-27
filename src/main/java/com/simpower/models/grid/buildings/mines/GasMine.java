package com.simpower.models.grid.buildings.mines;

import com.simpower.models.grid.GridInfos;

public class GasMine extends Mine {
    public GasMine(){
        super(100,  4000, 20, GridInfos.resourceStock.GAS);
        setResourceProduction(60);
    }
}
