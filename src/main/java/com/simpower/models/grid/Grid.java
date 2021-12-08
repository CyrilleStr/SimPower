package com.simpower.models.grid;

import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.HashMap;
import java.util.Map;


public class Grid implements GridInfos {
    //private double seed;
    //private ResourceAvailable availableResource;
    //private Clock clock;
    //private int citizens;
    private Cell[][] cells;

    /**
     * Instance a Grid, add the resource layer, add the top layer and show the top layer
     *
     * @param gridContainer
     */
    public Grid(GridPane gridContainer){
        this.generateEmptyGrid();
        this.addResourceLayer();
        this.addTopLayer();
        this.loadImg();
        this.showTopLayer(gridContainer);
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

    private void addResourceLayer(){
        // TODO implement Grid::addResourceLayer()
    }

    /**
     * Add the top layer : a road on top and random rivers
     */
    public void addTopLayer(){
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
        cells[14][0].setCurrentTopLayer(topLayer.VERTICAL_ROAD);
    }

    /**
     * Call the view to show the grid top layer
     *
     * @param gridContainer
     */
    public void showTopLayer(GridPane gridContainer) {
        for (int i = 0; i < NB_CELLS_HEIGHT; i++) {
            gridContainer.getRowConstraints().addAll(new RowConstraints(CELL_HEIGHT));

            for (int j = 0; j < NB_CELLS_WIDTH; j++) {
                gridContainer.getColumnConstraints().addAll(new ColumnConstraints(CELL_WIDTH));

                ImageView imgView = new ImageView(this.topLayerImages.get(cells[i][j].getCurrentTopLayer()));
                imgView.setFitWidth(CELL_WIDTH);
                imgView.setFitHeight(CELL_HEIGHT);

                /* Hovering effect */
                int finalI = i;
                int finalJ = j;
                imgView.hoverProperty().addListener((observable, oldVal, newVal) -> {
                    int X = cells[finalI][finalJ].getPos_x();
                    int Y = cells[finalI][finalJ].getPos_y();

                    ColorAdjust colorAdjust = new ColorAdjust();

                    if (newVal) colorAdjust.setBrightness(.5);
                    else colorAdjust.setBrightness(0);

                    imgView.setEffect(colorAdjust);
                });

                /* Add this event on mouse released only for test *
                imgView.onMouseReleasedProperty().set((event) -> {
                    this.change(event);
                });*/

                gridContainer.add(imgView, i, j);
            }
        }
    }

    /**
     * Call the view to show the grid resource layer
     *
     * @param gridContainer
     */
    public void showResourceLayer(GridPane gridContainer){
        // TODO implement Grid::showResourceLayer()
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/textures/map/water.png");
        ((ImageView) event.getSource()).setImage(resource);
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

    void loadImg(){
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/map/grass.png");
        Image topLayerVerticalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_2a.png");
        Image topLayerRiver = new Image("file:src/main/resources/com/simpower/assets/textures/map/water.png");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
        this.topLayerImages.put(topLayer.VERTICAL_ROAD,topLayerVerticalRoad);
        this.topLayerImages.put(topLayer.RIVER,topLayerRiver);
    }

    /**
     * Allows the player to create new roads in the game with a parse
     */
    public void addRoad(Cell cell){

        switch (this.cells[cell.getPos_x()-1][cell.getPos_y()].getCurrentTopLayer()){
            case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                switch (this.cells[cell.getPos_x()][cell.getPos_y()-1].getCurrentTopLayer()){
                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                            case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO 4 branches
                                        break;
                                    default:
                                        //TODO T envers
                                        break;
                                }
                            break;
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO T gauche
                                        break;
                                    default:
                                        //TODO virage haut droit
                                        break;
                                }
                        }
                    break;
                    default:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                            case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO T normal
                                    break;
                                    default:
                                        //TODO Droite horizontale
                                    break;
                                }
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO virage bas droit
                                    break;
                                    default:
                                        //TODO cul-de-sac droit
                                }
                                break;
                        }
                }
            break;
            default:
                switch (this.cells[cell.getPos_x()][cell.getPos_y()-1].getCurrentTopLayer()){
                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                            case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO T gauche
                                        break;
                                    default:
                                        //TODO virage haut gauche
                                        break;
                                }
                                break;
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO droit vertical
                                        break;
                                    default:
                                        //TODO cul-de-sac haut
                                        break;
                                }
                                break;
                        }
                    break;
                    default:
                        switch (this.cells[cell.getPos_x()+1][cell.getPos_y()].getCurrentTopLayer()){
                            case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO virage bas gauche
                                        break;
                                    default:
                                        //TODO cul-de-sac gauche
                                        break;
                                }
                                break;
                            default:
                                switch (this.cells[cell.getPos_x()][cell.getPos_y()+1].getCurrentTopLayer()){
                                    case VERTICAL_ROAD:case HORIZONTAL_ROAD:case TRI_ROAD:case TURNED_ROAD:case CROSS_ROAD:
                                        //TODO cul-de-sac bas
                                        break;
                                    default:
                                        //TODO Pas de route possible
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
