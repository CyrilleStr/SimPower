package com.simpower.models.grid;

import com.simpower.models.time.Clock;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.Map;

public class Grid implements GridInfos {
    private Cell[][] cells;
    private Label infoLabel;
    private GridPane gridContainer;
    private String buildingToDrop;
    private Map<String, buildingLayer> stringTopLayerMap = new HashMap<>();

    /**
     * Instance a Grid, add the resource layer, add the top layer and show the top layer
     * @param gridContainer
     */
    public Grid(GridPane gridContainer, Label infoLabel) {
        this.gridContainer = gridContainer;
        this.infoLabel = infoLabel;
        this.generateEmptyGrid();
        this.addResourceLayer();
        this.addTopLayer();
        this.addBuildingLayer();
        this.loadData();
        this.showLayers();
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
    private void addResourceLayer(){
        layResource(resourceLayer.COAL, 8);
        layResource(resourceLayer.OIL, 3);
        layResource(resourceLayer.URANIUM,3);
        layResource(resourceLayer.GAS,6);
    }

    /**
     * Add the top layer : a road on top and random rivers
     */
    private void addTopLayer() {
        for (int x = 0; x < NB_CELLS_WIDTH; x++) {
            for (int y = 0; y < NB_CELLS_HEIGHT; y++) {
                this.cells[x][y].setCurrentTopLayer(topLayer.GRASS);
            }
        }

        /* Generating nb € [1,3] river(s) */
        for (int nb = 0; nb < this.generateRandomInt(1, 3); nb++) this.generateRiver();
    }

    private void addBuildingLayer() {
        /* Set start road */
        this.cells[NB_CELLS_WIDTH/2][0].setCurrentBuildingLayer(buildingLayer.VERTICAL_ROAD);
    }

    /**
     * Draw circle of river
     * Rosetta Code implementation
     */
    private void drawRiver(int cx, int cy, int r) {
        int d = (5 - r * 4) / 4;
        int x = 0;
        int y = r;

        do {
            try { this.cells[cx + x][cy + y].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx + x][cy - y].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx - x][cy + y].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx - x][cy - y].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx + y][cy + x].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx + y][cy - x].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx - y][cy + x].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx - y][cy - x].setCurrentTopLayer(topLayer.RIVER); } catch (Throwable e) {}

            if (d < 0) d += 2 * x + 1;
            else {
                d += 2 * (x - y) + 1;
                y--;
            }

            x++;
        } while (x <= y);

        // draw smaller circle to "fill" the circle
        if (r - 1 > 0) drawRiver(cx, cy, r - 1);

        // filling empty spot
        this.cells[cx][cy].setCurrentTopLayer(topLayer.RIVER);
    }

    /**
     * Generate river through the map grid
     */
    private void generateRiver() {
        int step = 0;
        int Rr = this.generateRandomInt(1, 3);
        int Ry = 0;
        int Rx = this.generateRandomInt(0, NB_CELLS_WIDTH - 1);
        boolean vertical = this.generateRandomInt(0, 1) == 0 ? true : false;

        // Vertical OR horizontal river -> swap x & y coordinates
        if (vertical) {
            Ry = Rx;
            Rx = 0;
        }

        drawRiver(Rx, Ry, Rr);
        for (Ry = 1; Ry < (vertical ? NB_CELLS_HEIGHT : NB_CELLS_WIDTH); Ry++) {
            do {
                step = this.generateRandomInt(-1, 1);
            } while (step + Rx < 0 || step + Rx >= (vertical ? NB_CELLS_HEIGHT : NB_CELLS_WIDTH));

            Rx += step;
            drawRiver(Rx, Ry, Rr);
        }
        this.gridContainer.getColumnConstraints().addAll(new ColumnConstraints(CELL_WIDTH));
        this.gridContainer.getRowConstraints().addAll(new RowConstraints(CELL_HEIGHT));
    }

    /**
     * Set the building to drop
     *
     * @param buildingToDrop_p
     */
    public void setBuildingToDrop(String buildingToDrop_p){
        this.buildingToDrop = buildingToDrop_p;
    }

    /**
     * Small function used in hover listener when hovering a cell
     * @param imgView current img view hovered
     * @param x coordinate of the cell
     * @param y coordinate of the cell
     * @param newVal true if the cell is hovered
     */
    private void hoverListener(ImageView imgView, int x, int y, boolean newVal){
        this.infoLabel.setText(
            "X: " + cells[x][y].getPos_x()
            + " Y: " + cells[x][y].getPos_y()
            + " " + (cells[x][y].getCurrentTopLayer() == topLayer.NONE ? "" : cells[x][y].getCurrentTopLayer())
            + " " + (cells[x][y].getCurrentBuildingLayer() == buildingLayer.NONE ? "" : cells[x][y].getCurrentBuildingLayer())
            + " " + (cells[x][y].getCurrentPollutionLayer() == pollutionLayer.NONE ? "" : cells[x][y].getCurrentPollutionLayer())
            + " " + (cells[x][y].getCurrentResourceLayer() == resourceLayer.NONE ? "" : cells[x][y].getCurrentResourceLayer())
        );

        ColorAdjust colorAdjust = new ColorAdjust();

        // lighter when hovered, elsewhere, 0 (default brightness) (1 == white)
        if (newVal) colorAdjust.setBrightness(.5);
        else colorAdjust.setBrightness(0);

        imgView.setEffect(colorAdjust);
    }

    public void refreshLayers() {
        this.gridContainer = new GridPane();
        this.showLayers();
    }

    /**
     * Call the vue to show all layers
     */
    private void showLayers() {
        for (int x = 0; x < NB_CELLS_WIDTH; x++) {
            for (int y = 0; y < NB_CELLS_HEIGHT; y++) {

                int finalX = x;
                int finalY = y;

                ImageView topLayer = new ImageView(this.topLayerImages.get(cells[x][y].getCurrentTopLayer()));
                topLayer.setFitWidth(CELL_WIDTH);
                topLayer.setFitHeight(CELL_HEIGHT);
                topLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                    this.hoverListener(topLayer, finalX, finalY, newVal);
                });

                topLayer.setOnMouseDragReleased(mouseDragEvent -> {
                    ((ImageView) mouseDragEvent.getSource()).setImage(buildingLayerImages.get(stringTopLayerMap.get(buildingToDrop)));
                    cells[finalX][finalY].setCurrentBuildingLayer(stringTopLayerMap.get(buildingToDrop));
                    System.out.println(((ImageView) mouseDragEvent.getSource()).getId());
                });
                topLayer.setOnMouseDragOver(mouseDragEvent -> {
                    ((ImageView) mouseDragEvent.getSource()).setImage(buildingLayerImages.get(stringTopLayerMap.get(buildingToDrop)));
                });
                topLayer.setOnMouseDragExited(mouseDragEvent -> {
                    if(cells[finalX][finalY].getCurrentBuildingLayer() != stringTopLayerMap.get(buildingToDrop))
                        ((ImageView) mouseDragEvent.getSource()).setImage(topLayerImages.get(cells[finalX][finalY].getCurrentTopLayer()));
                });

                ImageView resourceLayer = new ImageView(this.resourceLayerImages.get(cells[x][y].getCurrentResourceLayer()));
                resourceLayer.setFitHeight(CELL_HEIGHT);
                resourceLayer.setFitWidth(CELL_WIDTH);
                resourceLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                    this.hoverListener(resourceLayer, finalX, finalY, newVal);
                });

                ImageView buildingLayer = new ImageView(this.buildingLayerImages.get(cells[x][y].getCurrentBuildingLayer()));
                buildingLayer.setFitHeight(CELL_HEIGHT);
                buildingLayer.setFitWidth(CELL_WIDTH);
                buildingLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                    this.hoverListener(buildingLayer, finalX, finalY, newVal);
                });

                ImageView pollutionLayer = new ImageView(this.pollutionLayerImages.get(cells[x][y].getCurrentPollutionLayer()));
                pollutionLayer.setFitHeight(CELL_HEIGHT);
                pollutionLayer.setFitWidth(CELL_WIDTH);
                pollutionLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                    this.hoverListener(pollutionLayer, finalX, finalY, newVal);
                });

                this.gridContainer.add(topLayer, x, y);
                this.gridContainer.add(resourceLayer, x, y);
                this.gridContainer.add(pollutionLayer, x, y);
                this.gridContainer.add(buildingLayer, x, y);
            }
        }

        this.gridContainer.getRowConstraints().addAll(new RowConstraints(CELL_HEIGHT));
        this.gridContainer.getColumnConstraints().addAll(new ColumnConstraints(CELL_WIDTH));
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
     * Loads all HashMap containing layer to image Map and string to top layer Map
     */
    void loadData(){
        /* Top layer */
        this.topLayerImages.put(topLayer.GRASS, new Image("file:src/main/resources/com/simpower/assets/textures/tile/grass.jpg"));
        this.topLayerImages.put(topLayer.RIVER, new Image("file:src/main/resources/com/simpower/assets/textures/tile/water.jpg"));
        this.topLayerImages.put(topLayer.SNOW, new Image("file:src/main/resources/com/simpower/assets/textures/tile/snow.jpg"));
        this.topLayerImages.put(topLayer.ICE, new Image("file:src/main/resources/com/simpower/assets/textures/tile/ice.jpg"));

        /* Building Layer */
        this.buildingLayerImages.put(buildingLayer.VERTICAL_ROAD, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_2a_v.png"));
        this.buildingLayerImages.put(buildingLayer.HOUSE, new Image("file:src/main/resources/com/simpower/assets/textures/buildings/house.jpg"));
        this.buildingLayerImages.put(buildingLayer.WORKING_BUILDING, new Image("file:src/main/resources/com/simpower/assets/textures/buildings/working_building.jpg"));

        /* Resource Layer */
        this.resourceLayerImages.put(resourceLayer.COAL, new Image("file:src/main/resources/com/simpower/assets/textures/resources/coal.png"));
        this.resourceLayerImages.put(resourceLayer.OIL, new Image("file:src/main/resources/com/simpower/assets/textures/resources/oil.png"));
        this.resourceLayerImages.put(resourceLayer.URANIUM, new Image("file:src/main/resources/com/simpower/assets/textures/resources/uranium.png"));
        this.resourceLayerImages.put(resourceLayer.GAS, new Image("file:src/main/resources/com/simpower/assets/textures/resources/gas.png"));

        /* String to top layer map */
        this.stringTopLayerMap.put("WORKING_BUILDING", buildingLayer.WORKING_BUILDING);
        this.stringTopLayerMap.put("HOUSE", buildingLayer.HOUSE);
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
        for (int i = 0; i<spread_value; i++) {
            do {
                do {
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
                } while ((0 > tmp_x) || (tmp_x > (NB_CELLS_WIDTH-1) ) || (0>tmp_y) || (tmp_y > (NB_CELLS_HEIGHT-1) ) );
            } while ((cells[tmp_x][tmp_y].getCurrentTopLayer() != topLayer.NONE) || (cells[tmp_x][tmp_y].getCurrentResourceLayer() != resourceLayer.NONE));

            x = tmp_x;
            y = tmp_y;
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
     * Allows the player to create new roads in the game with a parse
     *
     * @param cell the cell to study
     */
    public void addRoad(Cell cell){

        switch (this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentBuildingLayer()) {
            case VERTICAL_ROAD:
            case HORIZONTAL_ROAD:
            case TRI_ROAD:
            case TURNED_ROAD:
            case CROSS_ROAD:
            case END_ROAD:
                switch (this.cells[cell.getPos_x()][cell.getPos_y()-1].getCurrentBuildingLayer()){
                    case VERTICAL_ROAD:
                    case HORIZONTAL_ROAD:
                    case TRI_ROAD:
                    case TURNED_ROAD:
                    case CROSS_ROAD:
                    case END_ROAD:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentBuildingLayer()){
                            case VERTICAL_ROAD:
                            case HORIZONTAL_ROAD:
                            case TRI_ROAD:
                            case TURNED_ROAD:
                            case CROSS_ROAD:
                            case END_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //4 branches
                                        Image crossRoad = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_4.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.CROSS_ROAD);
                                        buildingLayerImages.put(buildingLayer.CROSS_ROAD, crossRoad);
                                        break;
                                    default:
                                        //T envers
                                        Image triRoadUpside = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_upside.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TRI_ROAD);
                                        buildingLayerImages.put(buildingLayer.TRI_ROAD, triRoadUpside);
                                        break;
                                }
                            break;
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //T gauche
                                        Image triRoadLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_left.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TRI_ROAD);
                                        buildingLayerImages.put(buildingLayer.TRI_ROAD, triRoadLeft);
                                        break;
                                    default:
                                        //virage haut droit
                                        Image turnedRoadTopRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2_hd.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TURNED_ROAD);
                                        buildingLayerImages.put(buildingLayer.TURNED_ROAD, turnedRoadTopRight);
                                        break;
                                }
                        }
                    break;
                    default:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentBuildingLayer()){
                            case VERTICAL_ROAD:
                            case HORIZONTAL_ROAD:
                            case TRI_ROAD:
                            case TURNED_ROAD:
                            case CROSS_ROAD:
                            case END_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //T normal
                                        Image triRoadNormal = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_normal.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TRI_ROAD);
                                        buildingLayerImages.put(buildingLayer.TRI_ROAD, triRoadNormal);
                                    break;
                                    default:
                                        //Droite horizontale
                                        Image horizontalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2a_h.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.HORIZONTAL_ROAD);
                                        buildingLayerImages.put(buildingLayer.HORIZONTAL_ROAD, horizontalRoad);
                                    break;
                                }
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //Virage bas droit
                                        Image turnedRoadBottomRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2b_bd.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TURNED_ROAD);
                                        buildingLayerImages.put(buildingLayer.TURNED_ROAD, turnedRoadBottomRight);
                                    break;
                                    default:
                                        //Cul-de-sac droit
                                        Image endRoadRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_d.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.END_ROAD);
                                        buildingLayerImages.put(buildingLayer.END_ROAD, endRoadRight);
                                }
                                break;
                        }
                }
            break;
            default:
                switch (this.cells[cell.getPos_x()][cell.getPos_y()-1].getCurrentBuildingLayer()){
                    case VERTICAL_ROAD:
                    case HORIZONTAL_ROAD:
                    case TRI_ROAD:
                    case TURNED_ROAD:
                    case CROSS_ROAD:
                    case END_ROAD:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentBuildingLayer()){
                            case VERTICAL_ROAD:
                            case HORIZONTAL_ROAD:
                            case TRI_ROAD:
                            case TURNED_ROAD:
                            case CROSS_ROAD:
                            case END_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //T droit
                                        Image triRoadRight = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_3_right.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TRI_ROAD);
                                        buildingLayerImages.put(buildingLayer.TRI_ROAD, triRoadRight);
                                        break;
                                    default:
                                        //Virage haut gauche
                                        Image turnedRoadTopLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2b_hg.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TURNED_ROAD);
                                        buildingLayerImages.put(buildingLayer.TURNED_ROAD, turnedRoadTopLeft);
                                        break;
                                }
                                break;
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //Droit vertical
                                        Image verticalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2a_v.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.VERTICAL_ROAD);
                                        buildingLayerImages.put(buildingLayer.VERTICAL_ROAD, verticalRoad);
                                        break;
                                    default:
                                        //Cul-de-sac haut
                                        Image endRoadTop = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_h.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.END_ROAD);
                                        buildingLayerImages.put(buildingLayer.END_ROAD, endRoadTop);
                                        break;
                                }
                                break;
                        }
                    break;
                    default:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentBuildingLayer()){
                            case VERTICAL_ROAD:
                            case HORIZONTAL_ROAD:
                            case TRI_ROAD:
                            case TURNED_ROAD:
                            case CROSS_ROAD:
                            case END_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //Virage bas gauche
                                        Image turnedRoadBottomLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_2b_bg.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.TURNED_ROAD);
                                        buildingLayerImages.put(buildingLayer.TURNED_ROAD, turnedRoadBottomLeft);
                                        break;
                                    default:
                                        //Cul-de-sac gauche
                                        Image endRoadLeft = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_g.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.END_ROAD);
                                        buildingLayerImages.put(buildingLayer.END_ROAD, endRoadLeft);
                                        break;
                                }
                                break;
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentBuildingLayer()){
                                    case VERTICAL_ROAD:
                                    case HORIZONTAL_ROAD:
                                    case TRI_ROAD:
                                    case TURNED_ROAD:
                                    case CROSS_ROAD:
                                    case END_ROAD:
                                        //Cul-de-sac bas
                                        Image endRoadBottom = new Image("file:src/main/resources/com/simpower/assets/textures/road/road_1_b.png");
                                        this.cells[cell.getPos_x()][cell.getPos_y()].setCurrentBuildingLayer(buildingLayer.END_ROAD);
                                        buildingLayerImages.put(buildingLayer.END_ROAD, endRoadBottom);
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

    /**
     * Update the cell next to a new road if a road was already there
     * @param cell cell to update
     */
    public void updateRoad(Cell cell){
        if (
            this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentBuildingLayer() == buildingLayer.TURNED_ROAD ||
            this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentBuildingLayer() == buildingLayer.END_ROAD ||
            this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentBuildingLayer() == buildingLayer.TRI_ROAD ||
            this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentBuildingLayer() == buildingLayer.CROSS_ROAD ||
            this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentBuildingLayer() == buildingLayer.VERTICAL_ROAD ||
            this.cells[cell.getPos_x()][cell.getPos_y()].getCurrentBuildingLayer() == buildingLayer.HORIZONTAL_ROAD
        ) addRoad(this.cells[cell.getPos_x()][cell.getPos_y()]);
    }

    /**
     * Function to use when you want to build a road. Combine addRoad() with updateRoad()
     * @param cell the cell where you want to add a new road
     */
    public void roadBuilder(Cell cell){
        addRoad(this.cells[cell.getPos_x()][cell.getPos_y()]);
        updateRoad(this.cells[cell.getPos_x()-1][cell.getPos_y()]);
        updateRoad(this.cells[cell.getPos_x()][cell.getPos_y()-1]);
        updateRoad(this.cells[cell.getPos_x()+1][cell.getPos_y()]);
        updateRoad(this.cells[cell.getPos_x()][cell.getPos_y()+1]);
    }

    public Cell[][] getCells() {
        return this.cells;
    }
}
