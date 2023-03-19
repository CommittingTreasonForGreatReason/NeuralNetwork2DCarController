package drawables;

import java.util.ArrayList;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import vectors.Vector2;

public class Minimap extends DrawableObject {
    
    private RaceTrack racetrack;
    private boolean isShown = true;
    private ArrayList<Vector2> carIndicators = new ArrayList<Vector2>();
    private double cellSize,width,height;
    
    public static final double scaleFactor = 0.2;

    public Minimap(Color baseColor, Vector2 centerPoint, RaceTrack racetrack) {
        super(baseColor, centerPoint);
        this.racetrack = racetrack;
        this.width = GUIController.getCanvasWidth()*scaleFactor;
        this.height = GUIController.getCanvasHeight()*scaleFactor;
        this.cellSize = width/racetrack.getGrid().getColumns();
    }
    
    public boolean isShown() {
        return isShown;
    }
    
    public void toggleIsShown() {
        isShown = !isShown;
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        ArrayList<Car> cars = racetrack.getCars();
        carIndicators.clear();
        for(Car car : cars) {
            double x = (car.getCenterX()-RaceTrack.getRaceTrackInstance().getCenterX())/GridCell.getSize()*cellSize + getCenterX();
            double y = (car.getCenterY()-RaceTrack.getRaceTrackInstance().getCenterY())/GridCell.getSize()*cellSize + getCenterY();
            carIndicators.add(new Vector2(x, y));
        }
    }
    
    
    // WIP
    @Override
    public void draw(GraphicsContext gc) {  
        GridCell[][] gridCells = racetrack.getGrid().getGridCells();
        int rows = racetrack.getGrid().getRows();
        int columns = racetrack.getGrid().getColumns();
        gc.setFill(baseColor);
        gc.fillRect(getCenterX()-width/2, getCenterY()-height/2, width, height);
        for (int i = 0;i<rows;i++) {
            for (int j = 0;j<columns;j++) {
                double x = getCenterX()-width/2+j*cellSize;
                double y = getCenterY()-height/2+i*cellSize;
                if(gridCells[i][j].isSpawn()) {
                    gc.setFill(Color.RED);
                }else if(gridCells[i][j].isWall()) {
                    gc.setFill(Constants.WALL_COLOR);
                }else {
                    gc.setFill(Constants.GROUND_COLOR);
                }
                gc.fillRect(x, y, cellSize+1, cellSize+1);
            }
        }
        gc.setFill(Color.RED);
        for(Vector2 carIndicators : carIndicators) {
            gc.fillOval(carIndicators.getX(), carIndicators.getY(), 5, 5);
        }
        gc.setLineWidth(1);
        gc.setStroke(Color.RED);
        gc.strokeRect(getCenterX()-width/2, getCenterY()-height/2, width, height);
    }

    @Override
    public void repositionGeometryOnResize() {
        

    }

}
