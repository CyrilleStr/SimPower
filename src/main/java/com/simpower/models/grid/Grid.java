package com.simpower.models.grid;

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
        this.addResourceLayer();
        this.addTopLayer();
        this.loadImg();
        this.showTopLayer(gridContainer);
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

    public void addResourceLayer(){
        // TODO implement Grid::addResourceLayer()
    }

    /**
     * Add the top layer : a road on top and random rivers
     */
    public void addTopLayer(){
        cells[14][0].setCurrentTopLayer(topLayer.VERTICAL_ROAD);
        // TODO Grid::addTopLayer() generate rivers
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
        // TODO implement Grid::showResourceLayer()
    }

    @FXML
    void change(MouseEvent event) {
        Image resource = new Image("file:src/main/resources/com/simpower/assets/textures/water.png");
        ((ImageView) event.getSource()).setImage(resource);
    }

    void loadImg(){
        Image topLayerNone = new Image("file:src/main/resources/com/simpower/assets/textures/ground.jpg");
        Image topLayerVerticalRoad = new Image("file:src/main/resources/com/simpower/assets/textures/verticalRoad.png");
        this.topLayerImages.put(topLayer.NONE,topLayerNone);
        this.topLayerImages.put(topLayer.VERTICAL_ROAD,topLayerVerticalRoad);
    }
}
