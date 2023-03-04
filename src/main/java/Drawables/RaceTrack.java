package Drawables;

import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import Vectors.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class RaceTrack extends DrawableObject{
    
    Vector2 vector2;
    double angle;
    private static RaceTrack raceTrack;
    
    Grid grid;
    Car car;

    private RaceTrack(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
        System.out.println("initialized RaceTrack:");
        
        vector2 = new Vector2(-2,2);
        
        car = new Car(Color.BLUE, new Vector2(centerPoint.getX(), centerPoint.getY()));
        Car.setSizes();
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
        
    }

    @Override
    public void draw(GraphicsContext gc) {
        grid.draw(gc);

        gc.setStroke(Color.RED);
        gc.setLineWidth(4);
        gc.strokeLine(centerPoint.getX()+vector2.getX()*30,centerPoint.getY()+vector2.getY()*30, centerPoint.getX(), centerPoint.getY());

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
