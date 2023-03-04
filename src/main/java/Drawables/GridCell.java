package Drawables;

import Vectors.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridCell extends DrawableObject{
    
    private static double size;
    
    public static void initSize(double gridSize, int gridResolution) {
        size = gridSize/gridResolution;
    }
    
    public static double getSize() {
        return size;
    }

    public GridCell(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        
        
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(baseColor);
        gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        
        
        gc.setStroke(Color.BLACK);
        gc.strokeRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        
//        System.out.println(centerPoint.getX()+"/"+centerPoint.getY());
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }

}
