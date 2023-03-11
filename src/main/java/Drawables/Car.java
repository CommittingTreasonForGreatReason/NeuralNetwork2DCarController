package drawables;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vectors.Vector2;
import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;

public class Car extends DrawableObject{
    
    private Vector2 velocity,desiredDirection;
    private Rectangle hitBoxRectangle;
    private double acceleration;
    private static final double deceleration = -20;
    private double rotationSpeed;
    private static double width,height;
    private static final double maxVelocity = 300;
    
    private boolean isCrashed = false;

    public Car(Vector2 centerPoint) {
        super(Constants.CAR_COLOR, centerPoint);
        velocity = new Vector2(1, 1);
        desiredDirection = new Vector2(1, 1);
        desiredDirection.setMagnitude(10);
        hitBoxRectangle = new Rectangle(centerPoint.getX()-width/2,centerPoint.getY()-width/2,width,width);
    }
    
    public static void setSizes(){
        height = GUIController.getCanvasWidth()*0.02;
        width = height*0.6;
    }
    
    public boolean isCrashed() {
        return isCrashed;
    }
    
    public void setCenterPoint(Vector2 centerPoint) {
        this.centerPoint = centerPoint;
        hitBoxRectangle = new Rectangle(centerPoint.getX()-width/2,centerPoint.getY()-width/2,width,width);
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        if(secondsSinceLastFrame<1 && !isCrashed) {
            move(secondsSinceLastFrame);
        }
        hitBoxRectangle = new Rectangle(centerPoint.getX()-width/2,centerPoint.getY()-width/2,width,width);
    }
    
    private void move(double secondsSinceLastFrame) {
        centerPoint = Vector2.add(centerPoint, Vector2.getScaledVector(velocity, secondsSinceLastFrame));
        desiredDirection.setAngle(desiredDirection.getAngle()+rotationSpeed*secondsSinceLastFrame);
        
        double speed = velocity.getMagnitude() + (acceleration+deceleration)*secondsSinceLastFrame;
        if(speed < 0) {
            speed = 0;
        }
        velocity = Vector2.add(velocity, Vector2.getScaledVector(desiredDirection, 2));
        velocity.setMagnitude(speed);
        if(velocity.getMagnitude() > maxVelocity) {
            velocity.setMagnitude(maxVelocity);
        }
    }
    
    public void updateCrashed(ArrayList<GridCell> wallGridCells) {
        for (GridCell wallGridCell : wallGridCells) {
            if(wallGridCell.intersects(hitBoxRectangle)) {
                 isCrashed = true;
                 baseColor = Constants.CAR_CRASHED_COLOR;
                 return;
            }
        }
    }
    

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(baseColor);
        gc.translate(centerPoint.getX(), centerPoint.getY());
        gc.rotate(-desiredDirection.getAngleInDegrees());
        gc.fillRect(-width/2, -height/2, width, height);
        gc.rotate(desiredDirection.getAngleInDegrees());
        gc.translate(-centerPoint.getX(), -centerPoint.getY());
        
//        gc.setStroke(Color.BLACK);
//        gc.setLineWidth(3);
//        gc.strokeRect(hitBoxRectangle.getX(), hitBoxRectangle.getY(), hitBoxRectangle.getWidth(), hitBoxRectangle.getHeight());
        if(!isCrashed) {
            drawVectors(gc);
        }
    }
    
    private void drawVectors(GraphicsContext gc) {
        gc.setStroke(Constants.VECTOR_COLOR);
        gc.strokeLine(centerPoint.getX(), centerPoint.getY(), centerPoint.getX() + desiredDirection.getX(), centerPoint.getY() + desiredDirection.getY());
        gc.setStroke(Color.GREEN);
        gc.strokeLine(centerPoint.getX(), centerPoint.getY(), centerPoint.getX() + velocity.getX(), centerPoint.getY() + velocity.getY());
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getText().equals("w")) {
            acceleration = 60;
        }else if(e.getText().equals("s")) {
            acceleration = -80;
        }else if(e.getText().equals("a")) {
            rotationSpeed = -120;
        }else if(e.getText().equals("d")) {
            rotationSpeed = 120;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        if(e.getText().equals("w")) {
            acceleration = 0;
        }else if(e.getText().equals("s")) {
            acceleration = 0;
        }else if(e.getText().equals("a")) {
            rotationSpeed = 0;
        }else if(e.getText().equals("d")) {
            rotationSpeed = 0;
        }
    }
    
}
