package com.simpower.models.grid;

import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private buildingLayer buildingAction;
    private boolean resourcesShown = false;
    private Map<buildingLayer,resourceLayer> buildingLayerToResourceLayerMap = new HashMap<>();

    /**
     * Instance a Grid, add the resource layer, add the top layer and show the top layer
     * @param gridContainer
     */
    public Grid(GridPane gridContainer, Label infoLabel, buildingLayer buildingType) {
        this.gridContainer = gridContainer;
        this.infoLabel = infoLabel;
        this.buildingAction = buildingType;
        this.generateEmptyGrid();
        this.addResourceLayer();
        this.addTopLayer();
        this.loadData();
        this.showLayers(false);
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
        for (int x = 0; x < NB_CELLS_WIDTH; x++) {
            for (int y = 0; y < NB_CELLS_HEIGHT; y++) {
                this.cells[x][y].setCurrentResourceLayer(resourceLayer.NONE);
            }
        }

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

    /**
     * Draw circle of river
     * Rosetta Code implementation
     */
    private void drawRiver(int cx, int cy, int r) {        int d = (5 - r * 4) / 4;
        int x = 0;
        int y = r;

        do {
            try { this.cells[cx + x][cy + y].setCurrentTopLayer(topLayer.RIVER); this.cells[cx + x][cy + y].setCurrentResourceLayer(resourceLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx + x][cy - y].setCurrentTopLayer(topLayer.RIVER); this.cells[cx + x][cy - y].setCurrentResourceLayer(resourceLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx - x][cy + y].setCurrentTopLayer(topLayer.RIVER); this.cells[cx - x][cy + y].setCurrentResourceLayer(resourceLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx - x][cy - y].setCurrentTopLayer(topLayer.RIVER); this.cells[cx - x][cy - y].setCurrentResourceLayer(resourceLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx + y][cy + x].setCurrentTopLayer(topLayer.RIVER); this.cells[cx + y][cy + x].setCurrentResourceLayer(resourceLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx + y][cy - x].setCurrentTopLayer(topLayer.RIVER); this.cells[cx + y][cy - x].setCurrentResourceLayer(resourceLayer.RIVER); } catch (Throwable e) {}
            try { this.cells[cx - y][cy + x].setCurrentTopLayer(topLayer.RIVER); this.cells[cx - y][cy + x].setCurrentResourceLayer(resourceLayer.RIVER);  } catch (Throwable e) {}
            try { this.cells[cx - y][cy - x].setCurrentTopLayer(topLayer.RIVER); this.cells[cx - y][cy - x].setCurrentResourceLayer(resourceLayer.RIVER); } catch (Throwable e) {}

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
        this.cells[cx][cy].setCurrentResourceLayer(resourceLayer.RIVER);
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
            + (cells[x][y].getCurrentTopLayer() == topLayer.NONE ? "" : (" " + cells[x][y].getCurrentTopLayer()))
            + (cells[x][y].getCurrentBuildingLayer() == buildingLayer.NONE ? "" : (" " + cells[x][y].getCurrentBuildingLayer()))
            + (cells[x][y].getCurrentPollutionLayer() == pollutionLayer.NONE ? "" : (" " + cells[x][y].getCurrentPollutionLayer()))
            + (cells[x][y].getCurrentResourceLayer() == resourceLayer.NONE ? "" : (" " + cells[x][y].getCurrentResourceLayer()))
        );

        ColorAdjust colorAdjust = new ColorAdjust();

        // lighter when hovered, elsewhere, 0 (default brightness) (1 == white)
        if (newVal) colorAdjust.setBrightness(.5);
        else colorAdjust.setBrightness(0);

        imgView.setEffect(colorAdjust);
    }

    /**
     * On mouse clicked event
     */
    private void mouseClicked(int x, int y) {
        switch (this.buildingAction) {
            case ROAD:
                this.roadBuilder(this.getCell(x, y));
                break;
            case WORKING_BUILDING:
            case HOUSE:
                this.buildingBuilder(this.getCell(x, y), this.buildingAction);
                break;
            case COAL_MINE:
            case OIL_MINE:
            case GAS_MINE:
            case URANIUM_MINE:
                if(this.checkMineRessource(this.getCell(x, y), this.buildingAction)){
                    this.buildingBuilder(this.getCell(x,y), this.buildingAction);
                }else{
                    //message d'erreur
                }

                break;
            case NONE:
                this.getCell(x, y).setCurrentBuildingLayer(buildingLayer.NONE);
                break;
        }

        this.refreshLayers();
    }

    public void refreshLayers() {
        this.gridContainer.getChildren().clear();
        this.showLayers(this.resourcesShown);
    }

    public void showResources() {
        this.resourcesShown = !this.resourcesShown;
        this.refreshLayers();
    }

    public void setBuilding(buildingLayer b){
        this.buildingAction = b;
    }

    public buildingLayer getBuildingAction() { return this.buildingAction; }

    /**
     * Call the vue to show all layers
     */
    private void showLayers(boolean notTop) {
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
                topLayer.setOnMouseClicked(event -> this.mouseClicked(finalX, finalY));

                ImageView resourceLayer = new ImageView(this.resourceLayerImages.get(cells[x][y].getCurrentResourceLayer()));
                resourceLayer.setFitHeight(CELL_HEIGHT);
                resourceLayer.setFitWidth(CELL_WIDTH);
                resourceLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                    this.hoverListener(resourceLayer, finalX, finalY, newVal);
                });
                resourceLayer.setOnMouseClicked(event -> this.mouseClicked(finalX, finalY));

                ImageView buildingLayer = new ImageView(this.buildingLayerImages.get(cells[x][y].getCurrentBuildingLayer()));
                buildingLayer.setFitHeight(CELL_HEIGHT);
                buildingLayer.setFitWidth(CELL_WIDTH);
                buildingLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                    this.hoverListener(buildingLayer, finalX, finalY, newVal);
                });
                buildingLayer.setOnMouseClicked(event -> this.mouseClicked(finalX, finalY));

                ImageView pollutionLayer = new ImageView(this.pollutionLayerImages.get(cells[x][y].getCurrentPollutionLayer()));
                pollutionLayer.setFitHeight(CELL_HEIGHT);
                pollutionLayer.setFitWidth(CELL_WIDTH);
                pollutionLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                    this.hoverListener(pollutionLayer, finalX, finalY, newVal);
                });

                if (notTop) this.gridContainer.add(resourceLayer, x, y);
                else this.gridContainer.add(topLayer, x, y);

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
        // -- roads
        this.buildingLayerImages.put(buildingLayer.ROAD, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_NORTH_SOUTH, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_north_south.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_WEST_EAST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_west_east.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_NORTH_EAST_SOUTH_WEST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_north_east_south_west.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_NORTH_EAST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_north_east.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_EAST_SOUTH, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_east_south.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_SOUTH_WEST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_south_west.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_WEST_NORTH, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_west_north.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_NORTH_EAST_SOUTH, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_north_east_south.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_EAST_SOUTH_WEST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_east_south_west.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_SOUTH_WEST_NORTH, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_south_west_north.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_WEST_NORTH_EAST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_west_north_east.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_NORTH, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_north.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_EAST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_east.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_SOUTH, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_south.png"));
        this.buildingLayerImages.put(buildingLayer.ROAD_WEST, new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_west.png"));
        this.buildingLayerImages.put(buildingLayer.COAL_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/coalmine.png"));
        this.buildingLayerImages.put(buildingLayer.OIL_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/oilmine.png"));
        this.buildingLayerImages.put(buildingLayer.GAS_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/gasmine.png"));
        this.buildingLayerImages.put(buildingLayer.URANIUM_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/uraniummine.png"));

        // -- houses & working building
        this.buildingLayerImages.put(buildingLayer.HOUSE, new Image("file:src/main/resources/com/simpower/assets/textures/buildings/houses/level_1/a.png"));
        this.buildingLayerImages.put(buildingLayer.WORKING_BUILDING, new Image("file:src/main/resources/com/simpower/assets/textures/buildings/working_building.jpg"));

        /* Resource Layer */
        this.resourceLayerImages.put(resourceLayer.NONE, new Image("file:src/main/resources/com/simpower/assets/textures/tile/dirt.jpg"));
        this.resourceLayerImages.put(resourceLayer.COAL, new Image("file:src/main/resources/com/simpower/assets/textures/resources/coal.png"));
        this.resourceLayerImages.put(resourceLayer.OIL, new Image("file:src/main/resources/com/simpower/assets/textures/resources/oil.png"));
        this.resourceLayerImages.put(resourceLayer.URANIUM, new Image("file:src/main/resources/com/simpower/assets/textures/resources/uranium.png"));
        this.resourceLayerImages.put(resourceLayer.GAS, new Image("file:src/main/resources/com/simpower/assets/textures/resources/gas.png"));
        this.resourceLayerImages.put(resourceLayer.RIVER, new Image("file:src/main/resources/com/simpower/assets/textures/tile/water.jpg"));

        /* Link resource layer to building layer */
        buildingLayerToResourceLayerMap.put(buildingLayer.COAL_MINE,resourceLayer.COAL);
        buildingLayerToResourceLayerMap.put(buildingLayer.GAS_MINE,resourceLayer.GAS);
        buildingLayerToResourceLayerMap.put(buildingLayer.URANIUM_MINE,resourceLayer.URANIUM);
        buildingLayerToResourceLayerMap.put(buildingLayer.OIL_MINE,resourceLayer.OIL);
    }

    /**
     * Add resources randomly next to the original cell
     *
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
        if (min >= max) throw new IllegalArgumentException("max must be greater than min");
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    /**
     * Tell if a cell exist
     * @param x coordinate
     * @param y coordinate
     * @return true if cell exist
     */
    private boolean isCellValid(int x, int y) {
        Cell tmp;
        try {
            tmp = this.cells[x][y];
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Tell if a building layer is a road
     * @param layer building layer
     * @return true if it's a road
     */
    private boolean isBuildingLayerRoad(buildingLayer layer) {
        switch (layer) {
            case ROAD,
                ROAD_NORTH_SOUTH,
                ROAD_WEST_EAST,
                ROAD_NORTH_EAST_SOUTH_WEST,
                ROAD_NORTH_EAST,
                ROAD_EAST_SOUTH,
                ROAD_SOUTH_WEST,
                ROAD_WEST_NORTH,
                ROAD_NORTH_EAST_SOUTH,
                ROAD_EAST_SOUTH_WEST,
                ROAD_SOUTH_WEST_NORTH,
                ROAD_WEST_NORTH_EAST,
                ROAD_NORTH,
                ROAD_EAST,
                ROAD_SOUTH,
                ROAD_WEST : return true;
            default: return false;
        }
    }

    /**
     * get the corresponding building layer following neighboor cells
     * @param x coordinate
     * @param y coordinate
     * @return corresponding building layer
     */
    private buildingLayer getCorrespondingBuildingLayer(int x, int y) {

        int neighboor = 0;
        boolean north = false;
        boolean west = false;
        boolean east = false;
        boolean south = false;

        /**
         *  .  y-1  .
         * x-1  c  x+1
         *  .  y+1  .
         */
        if (isCellValid(x, y - 1) && isBuildingLayerRoad(this.getCell(x, y - 1).getCurrentBuildingLayer())) {
            neighboor++;
            north = true;
        }
        if (isCellValid(x - 1, y) && isBuildingLayerRoad(this.getCell(x - 1, y).getCurrentBuildingLayer())) {
            neighboor++;
            west = true;
        }
        if (isCellValid(x + 1, y) && isBuildingLayerRoad(this.getCell(x + 1, y).getCurrentBuildingLayer())) {
            neighboor++;
            east = true;
        }
        if (isCellValid(x, y + 1) && isBuildingLayerRoad(this.getCell(x, y + 1).getCurrentBuildingLayer())) {
            neighboor++;
            south = true;
        }

        switch (neighboor) {
            case 1:
                if (north) return buildingLayer.ROAD_NORTH;
                if (south) return buildingLayer.ROAD_SOUTH;
                if (west) return buildingLayer.ROAD_WEST;
                if (east) return buildingLayer.ROAD_EAST;
            case 2:
                if (north && east) return buildingLayer.ROAD_NORTH_EAST;
                if (east && south) return buildingLayer.ROAD_EAST_SOUTH;
                if (south && west) return buildingLayer.ROAD_SOUTH_WEST;
                if (west && north) return buildingLayer.ROAD_WEST_NORTH;

                if (north && south) return buildingLayer.ROAD_NORTH_SOUTH;
                if (west && east) return buildingLayer.ROAD_WEST_EAST;

            case 3:
                if (north && east && south) return buildingLayer.ROAD_NORTH_EAST_SOUTH;
                if (east && south && west) return buildingLayer.ROAD_EAST_SOUTH_WEST;
                if (south && west && north) return buildingLayer.ROAD_SOUTH_WEST_NORTH;
                if (west && north && east) return buildingLayer.ROAD_WEST_NORTH_EAST;
            case 4:
                return buildingLayer.ROAD_NORTH_EAST_SOUTH_WEST;
            default:
            case 0: return buildingLayer.ROAD;
        }
    }

    /**
     * Place road on building layer
     * @param x coordinate
     * @param y coordinate
     * @param update tell if it's an update or a new placement
     */
    private void placeRoad(int x, int y, boolean update) {
        if (
            (this.getCell(x, y).getCurrentBuildingLayer() == buildingLayer.NONE && update == false) ||
            this.isBuildingLayerRoad(this.getCell(x, y).getCurrentBuildingLayer())
        ) {
            this.getCell(x, y).setCurrentBuildingLayer(this.getCorrespondingBuildingLayer(x, y));
        }

    }

    /**
     * Place a road & update cells around it
     * @param cell the cell where you want to add a new road
     */
    private void roadBuilder(Cell cell){
        /**
         * . p .
         * p o p
         * . p .
         */
        if (this.isCellValid(cell.getPos_x(), cell.getPos_y())) placeRoad(cell.getPos_x(), cell.getPos_y(), false);

        if (this.isCellValid(cell.getPos_x(), cell.getPos_y() - 1)) placeRoad(cell.getPos_x(), cell.getPos_y()-1, true);
        if (this.isCellValid(cell.getPos_x() - 1, cell.getPos_y())) placeRoad(cell.getPos_x()-1, cell.getPos_y(), true);
        if (this.isCellValid(cell.getPos_x() + 1, cell.getPos_y())) placeRoad(cell.getPos_x()+1, cell.getPos_y(), true);
        if (this.isCellValid(cell.getPos_x(), cell.getPos_y() + 1)) placeRoad(cell.getPos_x(), cell.getPos_y()+1, true);
    }

    /**
     * Place a building only if there is a road next to it
     * @param cell where the building is placed
     * @param buildingType what kind of building it is
     */
    private void buildingBuilder(Cell cell, buildingLayer buildingType) {
        /**
         * . p .
         * p o p
         * . p .
         */
        if (
            cell.getCurrentBuildingLayer() == buildingLayer.NONE && (
                this.isCellValid(cell.getPos_x(), cell.getPos_y() - 1) && this.isBuildingLayerRoad(this.getCell(cell.getPos_x(), cell.getPos_y() - 1).getCurrentBuildingLayer()) ||
                this.isCellValid(cell.getPos_x(), cell.getPos_y() + 1) && this.isBuildingLayerRoad(this.getCell(cell.getPos_x(), cell.getPos_y() + 1).getCurrentBuildingLayer()) ||
                this.isCellValid(cell.getPos_x() - 1, cell.getPos_y()) && this.isBuildingLayerRoad(this.getCell(cell.getPos_x() - 1, cell.getPos_y()).getCurrentBuildingLayer()) ||
                this.isCellValid(cell.getPos_x() + 1, cell.getPos_y()) && this.isBuildingLayerRoad(this.getCell(cell.getPos_x() + 1, cell.getPos_y()).getCurrentBuildingLayer())
            )
        ) {
            cell.setCurrentBuildingLayer(buildingType);
        }
    }

    private boolean checkMineRessource (Cell cell, buildingLayer buildingType){
        return (cell.getCurrentResourceLayer() == this.buildingLayerToResourceLayerMap.get(buildingType));
    }

    /**
     * get all the cells of the grid
     * @return cells
     */
    public Cell[][] getCells() {
        return this.cells;
    }

    /**
     * get the grid container (container of cells)
     * @return gridContainer
     */
    public GridPane getGridcontainer() {
        return this.gridContainer;
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Cell getCell(int x, int y) {
        Cell tmp;
        try {
            tmp = this.cells[x][y];
        } catch (Throwable e) {
            tmp = new Cell();
        }
        return tmp;
    }
}
