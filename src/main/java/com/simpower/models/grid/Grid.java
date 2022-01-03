package com.simpower.models.grid;

import com.simpower.controllers.GameController;
import com.simpower.models.grid.buildings.Building;
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
    private GameController gameController;
    private Cell[][] cells;
    private Label infoLabel;
    private Label errorLabel;
    private GridPane gridContainer;
    private String buildingToDrop;
    private buildingLayer buildingLayerAction;
    private Building buildingObjectAction;
    private boolean resourcesShown = false;
    private Map<buildingLayer,resourceLayer> buildingLayerToResourceLayerMap = new HashMap<>();


    /**
     * Instantiate a Grid, add the resource layer, add the top layer and show the top layer
     *
     * @param gridContainer GridPane
     * @param infoLabel Label
     * @param buildingType buildingLayer
     * @param errorLabel Label
     * @param gameController GameController
     */
    public Grid(GridPane gridContainer, Label infoLabel, buildingLayer buildingType, Label errorLabel, GameController gameController) {
        this.gridContainer = gridContainer;
        this.infoLabel = infoLabel;
        this.errorLabel = errorLabel;
        this.buildingLayerAction = buildingType;
        this.gameController = gameController;
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

        this.cells[roadStartX][0].setCurrentBuildingLayer(buildingLayer.ROAD_NORTH);
    }

    /**
     * Draw circle of river
     * Rosetta Code implementation
     *
     * @param cx int
     * @param cy int
     * @param r int
     */
    private void drawRiver(int cx, int cy, int r) {
        int d = (5 - r * 4) / 4;
        int x = 0;
        int y = r;

        do {
            if (this.isCellExist(cx + x, cy + y)) {
                this.getCell(cx + x, cy + y).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx + x, cy + y).setCurrentResourceLayer(resourceLayer.RIVER);
            }
            if (this.isCellExist(cx + x, cy - y)) {
                this.getCell(cx + x, cy - y).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx + x, cy - y).setCurrentResourceLayer(resourceLayer.RIVER);
            }
            if (this.isCellExist(cx - x, cy + y)) {
                this.getCell(cx - x, cy + y).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx - x, cy + y).setCurrentResourceLayer(resourceLayer.RIVER);
            }
            if (this.isCellExist(cx - x, cy - y)) {
                this.getCell(cx - x, cy - y).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx - x, cy - y).setCurrentResourceLayer(resourceLayer.RIVER);
            }
            if (this.isCellExist(cx + y, cy + x)) {
                this.getCell(cx + y, cy + x).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx + y, cy + x).setCurrentResourceLayer(resourceLayer.RIVER);
            }
            if (this.isCellExist(cx + y, cy - x)) {
                this.getCell(cx + y, cy - x).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx + y, cy - x).setCurrentResourceLayer(resourceLayer.RIVER);
            }
            if (this.isCellExist(cx - y, cy + x)) {
                this.getCell(cx - y, cy + x).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx - y, cy + x).setCurrentResourceLayer(resourceLayer.RIVER);
            }
            if (this.isCellExist(cx - y, cy - x)) {
                this.getCell(cx - y, cy - x).setCurrentTopLayer(topLayer.RIVER);
                this.getCell(cx - y, cy - x).setCurrentResourceLayer(resourceLayer.RIVER);
            }

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
     *
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

        // lighter when hovered, elsewhere, 0 (default brightness) (1 == full white)
        if (newVal) colorAdjust.setBrightness(.5);
        else colorAdjust.setBrightness(0);

        imgView.setEffect(colorAdjust);
    }

    /**
     * On mouse clicked event
     *
     * @param cell Cell to interact with
     * @return String containing an error message
     */
    public String mouseClicked(Cell cell) {
        String errorMsg = null;
        switch (this.getBuildingLayerAction()) {
            case ROAD:
                if(!this.lookAroundCell(placeRoad, cell))
                    errorMsg = "Roads can only be placed next to another road!";
                break;
            case HOUSE:
            case COAL_PLANT:
            case GAS_PLANT:
            case OIL_PLANT:
            case URANIUM_PLANT:
            case SOLAR_PLANT:
            case WIND_FARM:
                if(!this.lookAroundCell(placeBuilding, cell))
                    errorMsg = "Building can only be placed next to a road!";
                break;
            case WATER_MILL:
                if(!this.checkMineRessource(cell, buildingLayer.WATER_MILL))
                    errorMsg = "Watermill can only placed on a river!";
                else if(!this.lookAroundCell(placeBuilding, cell))
                    errorMsg = "Building can only be placed next to a road!";
                break;
            case COAL_MINE:
            case OIL_MINE:
            case GAS_MINE:
            case URANIUM_MINE:
                if (this.checkMineRessource(cell, this.getBuildingLayerAction())){
                    if(!this.lookAroundCell(placeBuilding, cell))
                        errorMsg = "Building can only be placed next to a road!";
                }else{
                    errorMsg = "There is no resources here!";
                }
                break;
            case DELETE:
                cell.setCurrentBuilding(null);
                cell.setCurrentBuildingLayer(buildingLayer.NONE);
                break;
            case NONE:
                break;
        }
        this.lookAroundCell(refreshCells, cell);
        return errorMsg;
    }

    /**
     * Refresh the different layers of the grid
     */
    public void refreshLayers() {
        this.gridContainer.getChildren().clear();
        this.showLayers(this.isResourcesShown());
    }

    /**
     * Build graphically the different layers of the map
     *
     * @param x x coordinates of a cell
     * @param y y coordinates of a cell
     * @param displayResourceLayer boolean to know if the resource layer is shown
     */
    private void constructLayers(int x, int y, boolean displayResourceLayer) {

        // Set an inactive effect (20% of brightness) on non-active buildings
        boolean isBuildingInactive = false;
        if(!this.getCell(x,y).isBuildingEmpty())
            if(!this.getCell(x,y).getCurrentBuilding().isRoad())
                if(!this.getCell(x, y).getCurrentBuilding().isActive())
                    isBuildingInactive = true;

        ColorAdjust inactiveEffect = new ColorAdjust();
        inactiveEffect.setBrightness(-0.8);

        if(displayResourceLayer) {
            ImageView resourceLayer = new ImageView(this.resourceLayerImages.get(cells[x][y].getCurrentResourceLayer()));
            resourceLayer.setFitHeight(CELL_HEIGHT);
            resourceLayer.setFitWidth(CELL_WIDTH);
            resourceLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                this.hoverListener(resourceLayer, x, y, newVal);
            });
            resourceLayer.setOnMouseClicked(event -> gameController.mouseClickedAction(this.getCell(x, y)));
            if(isBuildingInactive)
                resourceLayer.setEffect(inactiveEffect);
            this.gridContainer.add(resourceLayer,x,y);
        } else {
            ImageView topLayer = new ImageView(this.topLayerImages.get(cells[x][y].getCurrentTopLayer()));
            topLayer.setFitWidth(CELL_WIDTH);
            topLayer.setFitHeight(CELL_HEIGHT);
            topLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
                this.hoverListener(topLayer, x, y, newVal);
            });
            topLayer.setOnMouseClicked(event -> gameController.mouseClickedAction(this.getCell(x, y)));
            if(isBuildingInactive)
                topLayer.setEffect(inactiveEffect);
            this.gridContainer.add(topLayer,x,y);
        }

        ImageView buildingLayer = new ImageView(this.buildingLayerImages.get(cells[x][y].getCurrentBuildingLayer()));
        buildingLayer.setFitHeight(CELL_HEIGHT);
        buildingLayer.setFitWidth(CELL_WIDTH);
        buildingLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
            this.hoverListener(buildingLayer, x, y, newVal);
        });
        buildingLayer.setOnMouseClicked(event -> gameController.mouseClickedAction(this.getCell(x, y)));
        if(isBuildingInactive)
            buildingLayer.setEffect(inactiveEffect);
        this.gridContainer.add(buildingLayer, x, y);

        ImageView pollutionLayer = new ImageView(this.pollutionLayerImages.get(cells[x][y].getCurrentPollutionLayer()));
        pollutionLayer.setFitHeight(CELL_HEIGHT);
        pollutionLayer.setFitWidth(CELL_WIDTH);
        pollutionLayer.hoverProperty().addListener((_observable, _oldVal, newVal) -> {
            this.hoverListener(pollutionLayer, x, y, newVal);
        });
        if(isBuildingInactive)
            pollutionLayer.setEffect(inactiveEffect);
        this.gridContainer.add(pollutionLayer, x, y);


    }

    /**
     * Call the vue to show all layers
     *
     * @param notTop boolean
     */
    private void showLayers(boolean notTop) {
        for (int x = 0; x < NB_CELLS_WIDTH; x++) {
            for (int y = 0; y < NB_CELLS_HEIGHT; y++) {
                this.constructLayers(x, y, notTop);
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
        // todo: implement a for loop looking for files in corresponding dirs instead of a huge wall of code like below

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

        // --mines
        this.buildingLayerImages.put(buildingLayer.COAL_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/coalmine.png"));
        this.buildingLayerImages.put(buildingLayer.OIL_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/oilmine.png"));
        this.buildingLayerImages.put(buildingLayer.GAS_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/gasmine.png"));
        this.buildingLayerImages.put(buildingLayer.URANIUM_MINE, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/mines/uraniummine.png"));

        // --plants
        this.buildingLayerImages.put(buildingLayer.COAL_PLANT, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/plants/coalplant.png"));
        this.buildingLayerImages.put(buildingLayer.OIL_PLANT, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/plants/oilplant.png"));
        this.buildingLayerImages.put(buildingLayer.GAS_PLANT, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/plants/gasplant.png"));
        this.buildingLayerImages.put(buildingLayer.URANIUM_PLANT, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/plants/uraniumplant.png"));
        this.buildingLayerImages.put(buildingLayer.SOLAR_PLANT, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/plants/solarplant.png"));
        this.buildingLayerImages.put(buildingLayer.WIND_FARM, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/plants/windplant.png"));
        this.buildingLayerImages.put(buildingLayer.WATER_MILL, new Image ("file:src/main/resources/com/simpower/assets/textures/buildings/plants/waterplant.png"));

        // -- houses & working building
        this.buildingLayerImages.put(buildingLayer.HOUSE, new Image("file:src/main/resources/com/simpower/assets/textures/buildings/houses/level_1/a.png"));

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
        buildingLayerToResourceLayerMap.put(buildingLayer.WATER_MILL,resourceLayer.RIVER);
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
        int iterations =0;
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

                iterations++;
                if(iterations > spread_value+20){
                    return;
                }

            } while ((cells[tmp_x][tmp_y].getCurrentTopLayer() != topLayer.NONE) || (cells[tmp_x][tmp_y].getCurrentResourceLayer() != resourceLayer.NONE));
            x = tmp_x;
            y = tmp_y;
            cells[x][y].setCurrentResourceLayer(resourceType);
        }
    }

    /**
     * Change the boolean resourceShown value to display the resource layer
     */
    public void showResources() {
        this.resourcesShown = !this.resourcesShown;
        this.refreshLayers();
    }

    /**
     * Test if resourceShown is true of false
     *
     * @return boolean
     */
    public boolean isResourcesShown() {
        return this.resourcesShown;
    }

    /**
     * Set the building layer on player action
     *
     * @param b buildingLayer to set
     */
    public void setBuildingLayerAction(buildingLayer b){
        this.buildingLayerAction = b;
    }

    /**
     * Get the building layer on player action
     *
     * @return buildingLayer
     */
    public buildingLayer getBuildingLayerAction() {
        return this.buildingLayerAction;
    }

    /**
     * Set the building on player action
     *
     * @param buildingObjectAction Building to set
     */
    public void setBuildingObjectAction(Building buildingObjectAction) {
        this.buildingObjectAction = buildingObjectAction;
    }

    /**
     * Get the building on player action
     *
     * @return Building
     */
    public Building getBuildingObjectAction() {
        return buildingObjectAction;
    }

    /**
     * Generate a random number between min and max
     *
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
     *
     * @param x coordinate
     * @param y coordinate
     * @return true if cell exist
     */
    private boolean isCellExist(int x, int y) {
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
     *
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
                ROAD_WEST :
            return true;
        default:
            return false;
        }
    }

    /**
     * Get the corresponding building layer following neighbor cells
     *
     * @param x coordinate
     * @param y coordinate
     * @return corresponding building layer
     */
    private buildingLayer getCorrespondingBuildingLayerRoad(int x, int y) {
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
        if ((isCellExist(x, y - 1) && isBuildingLayerRoad(this.getCell(x, y - 1).getCurrentBuildingLayer())) || (x == roadStartX && y == 0)) {
            neighboor++;
            north = true;
        }
        if (isCellExist(x - 1, y) && isBuildingLayerRoad(this.getCell(x - 1, y).getCurrentBuildingLayer())) {
            neighboor++;
            west = true;
        }
        if (isCellExist(x + 1, y) && isBuildingLayerRoad(this.getCell(x + 1, y).getCurrentBuildingLayer())) {
            neighboor++;
            east = true;
        }
        if (isCellExist(x, y + 1) && isBuildingLayerRoad(this.getCell(x, y + 1).getCurrentBuildingLayer())) {
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
     */
    CellFunction placeRoad = (cells) -> {
        if (cells.length < 2) return false;
        boolean res = false;

        if (cells[0].getCurrentBuildingLayer() == buildingLayer.NONE && this.isBuildingLayerRoad(cells[1].getCurrentBuildingLayer())) {
            cells[0].setCurrentBuildingLayer(this.getCorrespondingBuildingLayerRoad(cells[0].getPos_x(), cells[0].getPos_y()));
            res = true;
        }

        if (this.isBuildingLayerRoad(cells[1].getCurrentBuildingLayer()))
            cells[1].setCurrentBuildingLayer(this.getCorrespondingBuildingLayerRoad(cells[1].getPos_x(), cells[1].getPos_y()));

        return res;
    };

    /**
     * Place building on building layer
     */
    CellFunction placeBuilding = (cells) -> {
        if (cells.length < 2) return false;

        if (cells[0].getCurrentBuildingLayer() == buildingLayer.NONE && this.isBuildingLayerRoad(cells[1].getCurrentBuildingLayer())) {
            cells[0].setCurrentBuilding(this.getBuildingObjectAction());
            cells[0].setCurrentBuildingLayer(this.getBuildingLayerAction());
            return true;
        }
        return false;
    };

    CellFunction refreshCells = (cells) -> {
        if (cells.length < 2) return false;
        constructLayers(cells[1].getPos_x(), cells[1].getPos_y(), this.isResourcesShown());
        return true;
    };

    // interface for lambda expressions
    interface CellFunction { boolean run(Cell... cell); }

    /**
     * Complex function to determine what operation should be done on all cells in the 3x3 area
     * around the center (cx, cy)
     *
     * @param function operation to be done
     * @param cell center cell
     * @return boolean
     */
    private boolean lookAroundCell(CellFunction function, Cell cell) {
        int goodResponse = 0;

        // center
        function.run(cell);
        // grid (+ center again)
        for (int x : new int[]{-1, 0, 1}) for (int y : new int[]{-1, 0, 1}) if (this.isCellExist(cell.getPos_x() + x, cell.getPos_y() + y)) {
            goodResponse += (function.run(cell, this.getCell(cell.getPos_x() + x, cell.getPos_y() + y))) ? 1 : 0;
        }

        // return error message if no response were good;
        return (goodResponse == 0) ? false : true;
    }

    /**
     * Check if a mine can be placed on a cell depending on resource presence
     *
     * @param cell Cell to test
     * @param buildingType Mine you want to place
     * @return boolean, true if can and false otherwise
     */
    private boolean checkMineRessource (Cell cell, buildingLayer buildingType){
        return (cell.getCurrentResourceLayer() == this.buildingLayerToResourceLayerMap.get(buildingType));
    }

    /**
     * Get all the cells of the grid
     *
     * @return cells
     */
    public Cell[][] getCells() {
        return this.cells;
    }

    /**
     * Get the grid container (container of cells)
     *
     * @return gridContainer
     */
    public GridPane getGridcontainer() {
        return this.gridContainer;
    }

    /**
     * Get the corresponding cell for the given coordinates
     *
     * @param x coordinate
     * @param y coordinate
     * @return Cell
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
