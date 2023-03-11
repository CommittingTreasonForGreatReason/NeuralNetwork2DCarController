package drawables;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import vectors.Vector2;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import NeuralNetworkGroup.NeuralNetworkArtifact.MarchingSquareHelper;

public class RaceTrack extends DrawableObject{
    
    private static RaceTrack raceTrack;
    
    private Grid grid;
    private final int amountOfCars = 100;
    private ArrayList<Car> cars;
    private ArrayList<Line2D> trackLines;

    private RaceTrack(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
        System.out.println("initialized RaceTrack:");
        Car.setSizes();
        grid = new Grid(Color.GRAY, new Vector2(GUIController.getCanvasWidth()/2, GUIController.getCanvasHeight()/2));
    }
    
    public static RaceTrack getRaceTrackInstance() {
        if (raceTrack == null) {
            raceTrack = new RaceTrack(Color.GRAY, new Vector2(GUIController.getCanvasWidth()/2, GUIController.getCanvasHeight()/2));
        }
        return raceTrack;
    }
    
    public void spawnCars() {
        ArrayList<GridCell> spawnGridCells = grid.getSpawnGridCells();
        cars = new ArrayList<Car>();
        for(int i = 0;i<amountOfCars;i++) {
            Vector2 carPosition;
            if(spawnGridCells.size()>0) {
                int index = (int)(Math.random() * spawnGridCells.size());
                Vector2 spawnPoint = spawnGridCells.get(index).centerPoint;
                carPosition = new Vector2(spawnPoint.getX(),spawnPoint.getY());
            }else {
                carPosition = new Vector2(centerPoint.getX(), centerPoint.getY());
            }
            cars.add(new Car(carPosition));
        }
        
        initTrackLines();
    }
    
    public void initTrackLines() {
        trackLines = MarchingSquareHelper.getMarchingSquareLines(grid);
    }
    
    public Grid getGrid() {
        return grid;
    }
    
    @Override
    public void update(double secondsSinceLastFrame) {
        for(Car car : cars) {
            car.update(secondsSinceLastFrame);
            if(!car.isCrashed()) {
                car.updateCrashed(grid.getWallGridCells());
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        grid.draw(gc);
        drawTrackLines(gc);
        for(Car car : cars) {
            car.draw(gc);
        }
    }
    
    private void drawTrackLines(GraphicsContext gc) {
        gc.setLineWidth(3);
        gc.setStroke(Constants.TRACKLINE_COLOR);
        for(Line2D line :trackLines) {
            gc.strokeLine(line.getX1(),line.getY1(), line.getX2(), line.getY2());
        }
    }

    @Override
    public void repositionGeometryOnResize() {
        
    }
    
    public void mouseClicked(final MouseEvent e) {
        grid.tryClickBoardCell(e);
        initTrackLines();
    }
    
    public void mouseDragged(final MouseEvent e) {
        grid.trySetHoverGridCell(new Point2D(e.getX(),e.getY()));
        grid.tryClickBoardCell(e);
        initTrackLines();
    }
    
    public void mouseMoved(Point2D mousePosition){
        grid.trySetHoverGridCell(mousePosition);
    }
    
    public void keyPressed(KeyEvent e) {
        for(Car car : cars) {
            car.keyPressed(e);
        }
        if(e.getText().equals("x")) {
            grid.toggleShowGridLines();
        }
    }
    
    public void keyReleased(KeyEvent e) {
        for(Car car : cars) {
            car.keyReleased(e);
        }
    }

}
