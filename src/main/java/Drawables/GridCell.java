package Drawables;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import Vectors.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GridCell extends DrawableObject{
    
    private static double size;
    private boolean isWall;
    
    public static void initSize(double gridSize, int gridResolution) {
        size = gridSize/gridResolution;
    }
    
    public boolean containsPoint(Point2D point2d) {
        Rectangle rectangle = new Rectangle(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        return rectangle.contains(point2d);
    }
    
    public boolean intersects(Rectangle rect) {
        Rectangle rectangle = new Rectangle(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        return rectangle.intersects(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
    }
    
    public GridCell(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
    }
    
    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }
    
    public boolean isWall() {
        return isWall;
    }
    
    public static double getSize() {
        return size;
    }

    @Override
    public void update(double secondsSinceLastFrame) {
          
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(isWall) {
            gc.setFill(Constants.WALL_COLOR);
        }else {
            gc.setFill(baseColor);
        }
        gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        
        
        gc.setStroke(Color.BLACK);
        gc.strokeRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
    }
    
    public void drawHover(GraphicsContext gc) {
        gc.setFill(Constants.HOVER_COLOR);
        gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
    }

    @Override
    public void repositionGeometryOnResize() {
        
    }

}
