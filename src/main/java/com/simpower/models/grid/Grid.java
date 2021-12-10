package com.simpower.models.grid;

import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Grid implements GridInfos {
    //private double seed;
    //private ResourceAvailable availableResource;
    //private Clock clock;
    //private int citizens;
    private Cell[][] cells;
    private GridPane gridContainer;

    /**
     * Instance a Grid, add the resource layer, add the top layer and show the top layer
     *
     * @param gridContainer
     */
    public Grid(GridPane gridContainer){
        this.gridContainer = gridContainer;
        this.generateEmptyGrid();
        this.addResourceLayer();
        this.addTopLayer();
        this.loadImg();
        this.showTopLayer();
        this.showResourceLayer();
    }

    /**
     * Generate an empty Grid while instancing cells
     */
    private void generateEmptyGrid(){
        this.cells = new Cell[NB_CELLS_HEIGHT][NB_CELLS_WIDTH];
        for (int y = 0; y < NB_CELLS_HEIGHT; y++) {
            for (int x = 0; x < NB_CELLS_WIDTH; x++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    /**
     * Add the resource layer : coal, gas, uranium, oil
     */
    public void addResourceLayer(){
        layResource(resourceLayer.COAL, 3);
        layResource(resourceLayer.OIL, 1);
        layResource(resourceLayer.URANIUM,0);
        layResource(resourceLayer.GAS,2);
    }

    /**
     * Add the top layer : a road on top and random rivers
     */
    public void addTopLayer() {
        /*Generate random river*/
        int isGenerateVertically = this.generateRandomInt(0,1);

        int step = 0;
        int y = 0;
        int x = this.generateRandomInt(0, NB_CELLS_WIDTH - 1);
        cells[(isGenerateVertically == 0) ? x : y][(isGenerateVertically == 0) ? y :x].setCurrentTopLayer(topLayer.RIVER);
        for (y = 1; y <((isGenerateVertically == 0) ? NB_CELLS_HEIGHT : NB_CELLS_WIDTH);  y++){
            do {
                step = this.generateRandomInt(-1,1);
            } while (step+x < 0 || step+x >= ((isGenerateVertically == 0) ? NB_CELLS_WIDTH : NB_CELLS_HEIGHT));
            x += step;
            cells[(isGenerateVertically == 0) ? x : y][(isGenerateVertically == 0) ? y :x].setCurrentTopLayer(topLayer.RIVER);
        }
        /*Set start road*/
        cells[NB_CELLS_WIDTH/2][0].setCurrentTopLayer(topLayer.VERTICAL_ROAD);
    }

    /**
     * Call the view to show the grid top layer
     */
    public void showTopLayer() {
        for (int i = 0; i < NB_CELLS_HEIGHT; i++) {
            this.gridContainer.getRowConstraints().addAll(new RowConstraints(CELL_HEIGHT));

            for (int j = 0; j < NB_CELLS_WIDTH; j++) {
                this.gridContainer.getColumnConstraints().addAll(new ColumnConstraints(CELL_WIDTH));

                ImageView imgView = new ImageView(this.topLayerImages.get(cells[i][j].getCurrentTopLayer()));
                imgView.setFitWidth(CELL_WIDTH);
                imgView.setFitHeight(CELL_HEIGHT);

                /* Hovering effect */
                int finalI = i;
                int finalJ = j;
                imgView.hoverProperty().addListener((observable, oldVal, newVal) -> {
                    ColorAdjust colorAdjust = new ColorAdjust();

                    // lighter when hovered, elsewhere, 0 (default brightness) (1 == white)
                    if (newVal) colorAdjust.setBrightness(.5);
                    else colorAdjust.setBrightness(0);

                    imgView.setEffect(colorAdjust);
                });

                this.gridContainer.add(imgView, i, j);
            }
        }
    }

    /**
     * Call the view to show the grid resource layer
     */
    public void showResourceLayer(){
        for(int i=0; i<NB_CELLS_HEIGHT; i++){
            for(int j=0; j<NB_CELLS_WIDTH; j++){
                ImageView imgView = new ImageView(this.resourceLayerImages.get(cells[i][j].getCurrentResourceLayer()));
                imgView.setFitHeight(CELL_HEIGHT);
                imgView.setFitWidth(CELL_HEIGHT);
                this.gridContainer.add(imgView,i,j);
            }
        }
    }

    /**
     * Lay resources randomly in each 4 chunks of the grid and calls spreadResource()
     *
     * @param resourceType the type of resource to spawn (included)
     * @param spread_value the number of resources to spawn when calling the Spread function(included)
     */
    public void layResource(resourceLayer resourceType, int spread_value){
        /*Divides the grid in 4 chunks and places a resource on a random cell in that chunk*/
        int posX, posY;
        int a=(NB_CELLS_HEIGHT/2) - 1;
        int b=(NB_CELLS_WIDTH/2) - 1;
        int countX =0;
        int countY =0;

        for(int i =0; i<NB_CELLS_HEIGHT; i+=( (NB_CELLS_HEIGHT/2) - 1)+countY){
            a+= i;
            for(int j=0; j<NB_CELLS_WIDTH ;j+=( (NB_CELLS_WIDTH/2) - 1)+countX){
                b+= j;
                do{
                    posX = this.generateRandomInt(j, b);
                    posY = this.generateRandomInt(i, a);
                }while(cells[posX][posY].getCurrentTopLayer() != topLayer.NONE);

                cells[posX][posY].setCurrentResourceLayer(resourceType);

                spreadResource(spread_value, resourceType, posX, posY);

                countX++;
            }
            b=14;
            countX=0;
            countY++;
        }
    }

    /**
     * Add resources randomly next to the original cell
     * @param spread_value the number of resources to spawn (included)
     * @param resourceType the type of resource to spawn (included)
     * @param x,y the coordinates of the original cell (included)
     * @return the random number
     */
    void spreadResource(int spread_value, resourceLayer resourceType, int x, int y){
        int tmp_x, tmp_y;
        for(int i=0; i<spread_value; i++){
            do {
                do{
                    tmp_x = x;
                    tmp_y = y;
                    int spread_side = generateRandomInt(0, 3);
                    switch(spread_side) {
                        case 0:
                            tmp_y -= 1;
                            break;
                        case 1:
                            tmp_x += 1;
                            break;
                        case 2:
                            tmp_y += 1;
                            break;
                        case 3:
                            tmp_x -= 1;
                            break;
                    }
                }while( (0 > tmp_x) || (tmp_x > (NB_CELLS_WIDTH-1) ) || (0>tmp_y) || (tmp_y > (NB_CELLS_HEIGHT-1) ) );
            }while( (cells[tmp_x][tmp_y].getCurrentTopLayer() != topLayer.NONE) || (cells[tmp_x][tmp_y].getCurrentResourceLayer() != resourceLayer.NONE));

            x=tmp_x;
            y=tmp_y;
            cells[x][y].setCurrentResourceLayer(resourceType);
        }
    }

    /**
     * Generate a random number between min and max
     * @param min the minimum value (included)
     * @param max the maximum value (included)
     * @return the random number
     */
    int generateRandomInt(int min, int max){
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    /**
     * Loads image for the map
     */
    void loadImg(){

        /*TOPLAYER*/
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/map/grass.jpg");
        Image topLayerVerticalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_2a_v.png");
        Image topLayerRiver = new Image("file:src/main/resources/com/simpower/assets/textures/map/water.jpg");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
        this.topLayerImages.put(topLayer.VERTICAL_ROAD,topLayerVerticalRoad);
        this.topLayerImages.put(topLayer.RIVER,topLayerRiver);

        /*RESOURCELAYER*/
        Image resourceLayerCoal = new Image("file:src/main/resources/com/simpower/assets/textures/map/coal.png");
        Image resourceLayerOil = new Image("file:src/main/resources/com/simpower/assets/textures/map/oil.png");
        Image resourceLayerUranium = new Image("file:src/main/resources/com/simpower/assets/textures/map/uranium.png");
        Image resourceLayerGas = new Image("file:src/main/resources/com/simpower/assets/textures/map/gas.png");
        this.resourceLayerImages.put(resourceLayer.COAL, resourceLayerCoal);
        this.resourceLayerImages.put(resourceLayer.OIL, resourceLayerOil);
        this.resourceLayerImages.put(resourceLayer.URANIUM, resourceLayerUranium);
        this.resourceLayerImages.put(resourceLayer.GAS, resourceLayerGas);

    }

    /**
     * Allows the player to create new roads in the game with a parse
     *
     * @param cell the cell to study
     */
    public void addRoad(Cell cell){

        //on vérifie que y a pas de bâtiment là
        if(this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.COAL_MINE && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.GAS_MINE && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.URANIUM_MINE && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.OIL_MINE && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.COAL_GENERATOR && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.GAS_GENERATOR && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.OIL_GENERATOR && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.URANIUM_GENERATOR && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.SOLAR_GENERATOR && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.WATER_GENERATOR && this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer() != topLayer.WIND_GENERATOR){
            switch (this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer()){
                case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                    switch (this.cells[cell.getPos_x()][cell.getPos_y()-1].getCurrentTopLayer()){
                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                            switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                                case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //4 branches
                                            Image crossRoad = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_4.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.CROSS_ROAD);
                                            topLayerImages.put(topLayer.CROSS_ROAD, crossRoad);
                                            break;
                                        default:
                                            //T envers
                                            Image triRoadUpside = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_upside.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TRI_ROAD);
                                            topLayerImages.put(topLayer.TRI_ROAD, triRoadUpside);
                                            break;
                                    }
                                    break;
                                default:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //T gauche
                                            Image triRoadLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_left.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TRI_ROAD);
                                            topLayerImages.put(topLayer.TRI_ROAD, triRoadLeft);
                                            break;
                                        default:
                                            //virage haut droit
                                            Image turnedRoadTopRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2_hd.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TURNED_ROAD);
                                            topLayerImages.put(topLayer.TURNED_ROAD, turnedRoadTopRight);
                                            break;
                                    }
                            }
                            break;
                        default:
                            switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                                case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //T normal
                                            Image triRoadNormal = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_normal.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TRI_ROAD);
                                            topLayerImages.put(topLayer.TRI_ROAD, triRoadNormal);
                                            break;
                                        default:
                                            //Droite horizontale
                                            Image horizontalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2a_h.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.HORIZONTAL_ROAD);
                                            topLayerImages.put(topLayer.HORIZONTAL_ROAD, horizontalRoad);
                                            break;
                                    }
                                default:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //Virage bas droit
                                            Image turnedRoadBottomRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2b_bd.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TURNED_ROAD);
                                            topLayerImages.put(topLayer.TURNED_ROAD, turnedRoadBottomRight);
                                            break;
                                        default:
                                            //Cul-de-sac droit
                                            Image endRoadRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_d.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.END_ROAD);
                                            topLayerImages.put(topLayer.END_ROAD, endRoadRight);
                                    }
                                    break;
                            }
                    }
                    break;
                default:
                    switch (this.cells[cell.getPos_x()][cell.getPos_y()-1].getCurrentTopLayer()){
                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                            switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                                case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //T droit
                                            Image triRoadRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_right.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TRI_ROAD);
                                            topLayerImages.put(topLayer.TRI_ROAD, triRoadRight);
                                            break;
                                        default:
                                            //Virage haut gauche
                                            Image turnedRoadTopLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2b_hg.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TURNED_ROAD);
                                            topLayerImages.put(topLayer.TURNED_ROAD, turnedRoadTopLeft);
                                            break;
                                    }
                                    break;
                                default:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //Droit vertical
                                            Image verticalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2a_v.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.VERTICAL_ROAD);
                                            topLayerImages.put(topLayer.VERTICAL_ROAD, verticalRoad);
                                            break;
                                        default:
                                            //Cul-de-sac haut
                                            Image endRoadTop = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_h.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.END_ROAD);
                                            topLayerImages.put(topLayer.END_ROAD, endRoadTop);
                                            break;
                                    }
                                    break;
                            }
                            break;
                        default:
                            switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                                case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //Virage bas gauche
                                            Image turnedRoadBottomLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2b_bg.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.TURNED_ROAD);
                                            topLayerImages.put(topLayer.TURNED_ROAD, turnedRoadBottomLeft);
                                            break;
                                        default:
                                            //Cul-de-sac gauche
                                            Image endRoadLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_g.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.END_ROAD);
                                            topLayerImages.put(topLayer.END_ROAD, endRoadLeft);
                                            break;
                                    }
                                    break;
                                default:
                                    switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                        case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:case END_ROAD:
                                            //Cul-de-sac bas
                                            Image endRoadBottom = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_b.png");
                                            this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentTopLayer(topLayer.END_ROAD);
                                            topLayerImages.put(topLayer.END_ROAD, endRoadBottom);
                                            break;
                                        default:
                                            //TODO Pas de route possible
                                            //Faire une info-bulle sur la souris ou un truc du genre
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }


    }

    /**
     * Update the cell next to a new road if a road was already there
     * @param cell cell to update
     */
    public void updateRoad(Cell cell){
        if(this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentTopLayer() == topLayer.TURNED_ROAD || this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentTopLayer() == topLayer.END_ROAD || this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentTopLayer() == topLayer.TRI_ROAD || this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentTopLayer() == topLayer.CROSS_ROAD || this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentTopLayer() == topLayer.VERTICAL_ROAD || this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentTopLayer() == topLayer.HORIZONTAL_ROAD){
            addRoad(this.cells[cell.getPos_x()][cell.getPos_y()]);
        }
    }

    /**
     * Function to use when you wanna build a road. Combine addRoad() with updateRoad()
     * @param cell the cell where you wanna add a new road
     */
    public void roadBuilder(Cell cell){
        addRoad(this.cells[cell.getPos_x()][cell.getPos_y()]);
        updateRoad(this.cells[cell.getPos_x()-1][cell.getPos_y()]);
        updateRoad(this.cells[cell.getPos_x()][cell.getPos_y()-1]);
        updateRoad(this.cells[cell.getPos_x()+1][cell.getPos_y()]);
        updateRoad(this.cells[cell.getPos_x()][cell.getPos_y()+1]);
    }
}
