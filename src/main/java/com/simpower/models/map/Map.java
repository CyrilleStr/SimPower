package com.simpower.models.map;

import com.simpower.models.time.Clock;

public class Map {
    private int size;    //longueur d'un coté (si size = 16, alors map 16x16)
    private ResourceAvailable availableResource = new ResourceAvailable();
    private final Clock clock = new Clock();
    private Slot[] slots = new Slot[size*size];
    private int citizens = 0;

    public Map(int sizeS){
        setSize(sizeS);
        generateMap(this.slots, this.size);
    }

    public void setSize(int sizeS){
        this.size = sizeS;
    }

    public int getCitizens() {
        return this.citizens;
    }

    private void generateMap(Slot[] slotsS, int sizeS){

        int i = 0;
        int j = 0;

        for(int k = 0; k < sizeS*sizeS; k++) {

            slotsS[k].setPos_x(i);
            slotsS[k].setPos_y(j);

            if(j < sizeS){
                j++;
            }
            else{
                j = 0;
            }

            if(j == 0){
                i++;
            }
        }
        generateRiver();
    }

    private void generateRiver(){
        //faut faire un truc pour générer une rivière, j'ai pas trop d'inspi
    }
}
