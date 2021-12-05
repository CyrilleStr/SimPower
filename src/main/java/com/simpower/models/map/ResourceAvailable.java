package com.simpower.models.map;

import com.simpower.models.map.resources.Coal;
import com.simpower.models.map.resources.Gas;
import com.simpower.models.map.resources.Oil;
import com.simpower.models.map.resources.Uranium;

import java.util.List;

public class ResourceAvailable extends Map {
    private List<Coal> coalList;
    private List<Oil> oilList;
    private List<Uranium> uraniumList;
    private List<Gas> gasList;

    public ResourceAvailable(){
    }
}
