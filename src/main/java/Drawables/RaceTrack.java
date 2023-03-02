package Drawables;

import Vectors.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RaceTrack extends DrawableObject{
    
    Vector2 vector2;
    double angle;

    public RaceTrack(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
        System.out.println("initialized RaceTrack:");
        
        vector2 = new Vector2(-2,2);
//        vector2.setAngle(Math.toRadians(90));
        System.out.println(vector2);
        vector2.setX(5);
        System.out.println(vector2);
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        
        
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(4);
        gc.strokeLine(centerPoint.getX()+vector2.getX()*30,centerPoint.getY()+vector2.getY()*30, centerPoint.getX(), centerPoint.getY());
        gc.setFill(Color.RED);
        gc.translate(centerPoint.getX(), centerPoint.getY());
        gc.rotate(Math.toDegrees(angle));
        gc.fillRect(-10, -10, 20, 20);
        gc.rotate(Math.toDegrees(-angle));
        gc.translate(-centerPoint.getX(), -centerPoint.getY());
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }
    
    public void mouseMoved(Point2D mousePosition){
        angle = -Math.atan2(mousePosition.getX()-centerPoint.getX(),mousePosition.getY()-centerPoint.getY())+Math.PI/2;
        vector2.setAngle(angle);
    }

}
