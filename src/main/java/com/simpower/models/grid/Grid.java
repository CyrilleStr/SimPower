package com.simpower.models.grid;

import javafx.fxml.FXML;
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
        this.addResourceLayer();
        this.addTopLayer();
        this.loadImg();
        this.showTopLayer(gridContainer);
    }

    /**
     * Generate an empty Grid while instancing cells
     */
    private void generateEmptyGrid(){
        this.cells = new Cell[NB_CELLS_WIDTH][NB_CELLS_HEIGHT];
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
    private void addTopLayer(){
        cells[14][0].setCurrentTopLayer(topLayer.VERTICAL_ROAD);
        // TODO Grid::addTopLayer() generate rivers
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
        // TODO implement Grid::showResourceLayer()
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/textures/map/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }

    private void loadImg(){
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/map/grass.png");
        Image topLayerVerticalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/roads/road_2.png");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
        this.topLayerImages.put(topLayer.VERTICAL_ROAD,topLayerVerticalRoad);
    }
}
