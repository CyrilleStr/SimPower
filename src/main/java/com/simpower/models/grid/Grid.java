package com.simpower.models.grid;

import com.simpower.models.grid.resources.Coal;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;


public class Grid implements GridInfos {
    //private double seed;
    //private ResourceAvailable availableResource;
    //private Clock clock;
    //private int citizens;
    private Cell[][] cells;
    private final Map<resourceLayer,Image> resourceLayerImages = new HashMap<>();
    private final Map<topLayer,Image> topLayerImages = new HashMap<>();
    private final Map<pollutionLayer,Image> pollutionLayerImages = new HashMap<>();

    /**
     * Instance a Grid, add the resource layer, add the top layer and show the top layer
     *
     * @param gridContainer
     */
    public Grid(GridPane gridContainer){
        this.generateEmptyGrid();
        this.addTopLayer();
        this.addResourceLayer();
        this.loadImg();
        this.showTopLayer(gridContainer);
        this.showResourceLayer(gridContainer);
    }

    /**
     * Generate an empty Grid while instancing cells
     */
    public void generateEmptyGrid(){
        cells = new Cell[X_SIZE][Y_SIZE];
        for (int y = 0; y < Y_SIZE; y++) {
            for (int x = 0; x < X_SIZE; x++) {
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
    public void addTopLayer(){
        /*Generate random river*/
        int isGenerateVertically = this.generateRandomInt(0,1);

        int step = 0;
        int y = 0;
        int x = this.generateRandomInt(0,X_SIZE-1);
        cells[(isGenerateVertically == 0) ? x : y][(isGenerateVertically == 0) ? y :x].setCurrentTopLayer(topLayer.RIVER);
        for(y = 1; y <((isGenerateVertically == 0) ? Y_SIZE :X_SIZE);y++){
            do{
                step = this.generateRandomInt(-1,1);
            }while(step+x < 0 || step+x >= ((isGenerateVertically == 0) ? X_SIZE : Y_SIZE));
            x += step;
            cells[(isGenerateVertically == 0) ? x : y][(isGenerateVertically == 0) ? y :x].setCurrentTopLayer(topLayer.RIVER);
        }
        /*Set start road*/
        cells[14][0].setCurrentTopLayer(topLayer.VERTICAL_ROAD);
    }

    /**
     * Call the view to show the grid top layer
     *
     * @param gridContainer
     */
    public void showTopLayer(GridPane gridContainer) {
        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                ImageView imgView = new ImageView(this.topLayerImages.get(cells[i][j].getCurrentTopLayer()));
                imgView.setFitWidth(WIDTH_SLOT);
                imgView.setFitHeight(HEIGHT_SLOT);
                /* Add this event on mouse released only for test */
                imgView.onMouseReleasedProperty().set((event) -> {
                    this.change(event);
                });
                gridContainer.add(imgView,i,j);
            }
        }
    }

    /**
     * Call the view to show the grid resource layer
     *
     * @param gridContainer
     */
    public void showResourceLayer(GridPane gridContainer){
        for(int i=0; i<Y_SIZE; i++){
            for(int j=0; j<X_SIZE; j++){
                ImageView imgView = new ImageView(this.resourceLayerImages.get(cells[i][j].getCurrentResourceLayer()));
                imgView.setFitHeight(HEIGHT_SLOT);
                imgView.setFitWidth(WIDTH_SLOT);
                gridContainer.add(imgView,i,j);
            }
        }
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/textures/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }

    /**
     * Generate a random number between min and max
     * @param min the minimum value (included)
     * @param max the maximum value (included)
     */
    int generateRandomInt(int min, int max){
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    /**
     * Lay resources randomly in each 4 chunks of the grid and calls spreadResource()
     * @param resourceType the type of resource to spawn (included)
     * @param spread_value the number of resources to spawn when calling the Spread function(included)
     */
    public void layResource(resourceLayer resourceType, int spread_value){
        /*Divides the grid in 4 chunks and places a resource on a random cell in that chunk*/
        int posX, posY;
        int a=14;
        int b=14;
        int countX =0;
        int countY =0;

        for(int i =0; i<Y_SIZE; i+=14+countY){
            a+= i;
            for(int j=0; j<X_SIZE; j+=14+countX){
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
                }while( (0>tmp_x) || (tmp_x>29) || (0>tmp_y) || (tmp_y>29) );
            }while( (cells[tmp_x][tmp_y].getCurrentTopLayer() != topLayer.NONE) || (cells[tmp_x][tmp_y].getCurrentResourceLayer() != resourceLayer.NONE));

            x=tmp_x;
            y=tmp_y;
            System.out.println("x :"+x+" y :"+y);
            cells[x][y].setCurrentResourceLayer(resourceType);
        }
    }
    void loadImg(){
        /*TOPLAYER*/
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/ground.jpg");
        Image topLayerVerticalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/verticalRoad.png");
        Image topLayerRiver = new Image("file:src/main/resources/com/simpower/assets/textures/river.jpg");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
        this.topLayerImages.put(topLayer.VERTICAL_ROAD,topLayerVerticalRoad);
        this.topLayerImages.put(topLayer.RIVER,topLayerRiver);

        /*RESOURCELAYER*/
        Image resourceLayerCoal = new Image("file:src/main/resources/com/simpower/assets/textures/coal.png");
        Image resourceLayerOil = new Image("file:src/main/resources/com/simpower/assets/textures/oil.png");
        Image resourceLayerUranium = new Image("file:src/main/resources/com/simpower/assets/textures/uranium.png");
        Image resourceLayerGas = new Image("file:src/main/resources/com/simpower/assets/textures/gas.png");
        this.resourceLayerImages.put(resourceLayer.COAL, resourceLayerCoal);
        this.resourceLayerImages.put(resourceLayer.OIL, resourceLayerOil);
        this.resourceLayerImages.put(resourceLayer.URANIUM, resourceLayerUranium);
        this.resourceLayerImages.put(resourceLayer.GAS, resourceLayerGas);

    }
}
