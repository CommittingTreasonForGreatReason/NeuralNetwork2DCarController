package Drawables;

import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import Vectors.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RaceTrack extends DrawableObject{
    
    private static RaceTrack raceTrack;
    
    private Grid grid;
    private Car car;

    private RaceTrack(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
        System.out.println("initialized RaceTrack:");
        
        Car.setSizes();
        car = new Car(new Vector2(centerPoint.getX(), centerPoint.getY()));
        
        grid = new Grid(Color.GRAY, new Vector2(GUIController.getCanvasWidth()/2, GUIController.getCanvasHeight()/2), GUIController.getCanvasWidth()/2);
    }
    
    public static RaceTrack getRaceTrackInstance() {
        if (raceTrack == null) {
            raceTrack = new RaceTrack(Color.GRAY, new Vector2(GUIController.getCanvasWidth()/2, GUIController.getCanvasHeight()/2));
        }
        return raceTrack;
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        car.update(secondsSinceLastFrame);
        if(!car.isCrashed()) {
            car.updateCrashed(grid.getWallGridCells());
        }
        
    }

    @Override
    public void draw(GraphicsContext gc) {
        
        grid.draw(gc);
        car.draw(gc);
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }
    
    public void mouseClicked(final MouseEvent e) {
        grid.tryClickBoardCell(e);
    }
    
    public void mouseDragged(final MouseEvent e) {
        grid.trySetHoverGridCell(new Point2D(e.getX(),e.getY()));
        grid.tryClickBoardCell(e);
    }
    
    public void mouseMoved(Point2D mousePosition){
        grid.trySetHoverGridCell(mousePosition);
    }
    
    public void keyPressed(KeyEvent e) {
        car.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e) {
        car.keyReleased(e);
    }

}