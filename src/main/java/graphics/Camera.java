package graphics;

import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import vectors.Vector2;

public class Camera {
    
    public static Camera camera;
    
    private Vector2 position,velocity;
    private double speed = 5;
    
    private Camera(double x, double y) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
    }
    
    public static Camera getCameraInstance(double x, double y) {
        if (camera == null) {
            camera = new Camera(x,y);
        }
        return camera;
    }
    
    public static Camera getCameraInstance() {
        return camera;
    }
    
    public double getX(){
        return position.getX() - GUIController.getCanvasWidth()/2;
    }
    public double getY(){
        return position.getY() - GUIController.getCanvasHeight()/2;
    }
    public double getCenterX() {
        return position.getX();
    }
    public double getCenterY() {
        return position.getY();
    }
    
    public void follow(int x, int y) {
        position = new Vector2(x, y);
    }
    
    public void move() {
        position = Vector2.add(position, velocity);
    }
    
    public void keyPressed(KeyEvent e) {
        if(e.getCode() == KeyCode.UP) {
            velocity.setY(-speed);
        }else if(e.getCode() == KeyCode.DOWN) {
            velocity.setY(speed);
        }else if(e.getCode() == KeyCode.LEFT) {
            velocity.setX(-speed);
        }else if(e.getCode() == KeyCode.RIGHT) {
            velocity.setX(speed);
        }
        
    }
    public void keyReleased(KeyEvent e) {
        if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
            velocity.setY(0);
        }else if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
            velocity.setX(0);
        }
    }
}
