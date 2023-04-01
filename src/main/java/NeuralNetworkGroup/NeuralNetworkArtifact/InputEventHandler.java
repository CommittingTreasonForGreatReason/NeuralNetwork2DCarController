package NeuralNetworkGroup.NeuralNetworkArtifact;

import drawables.Car;
import drawables.RaceTrack;
import graphics.Camera;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class InputEventHandler {
    private static InputEventHandler inputEventHandler;
    
    private boolean shiftDown = false;
    private byte scrollIndex = 0;
    private final byte maxScrollIndex=2;
    
    private RaceTrack raceTrack;
    private Camera camera;
    
    public InputEventHandler() {
        raceTrack = RaceTrack.getRaceTrackInstance();
        camera = Camera.getCameraInstance();
    }
    
    public static InputEventHandler getInputEventHandlerInstance() {
        if (inputEventHandler == null) {
            inputEventHandler = new InputEventHandler();
        }
        return inputEventHandler;
    }
    public void mouseScrolled(ScrollEvent e) {
        mouseScroll(e.getDeltaY()>0);
    }
    public void mouseClicked(final MouseEvent e) {
        raceTrack.mouseClicked(e,false,scrollIndex,shiftDown);
    }
    public void mouseDragged(final MouseEvent e) {
        mouseMoved(e);
        raceTrack.mouseClicked(e,true,scrollIndex,shiftDown);
    }
    
    public void mouseMoved(final MouseEvent e){
        Point2D mousePosition = new Point2D(e.getX()+camera.getX(), e.getY()+camera.getY());
        raceTrack.movedMouse(mousePosition);
    }
    
    public void keyPressed(KeyEvent e) {
        for(Car car : raceTrack.getCars()) {
            car.keyPressed(e);
        }
        if(e.getText().equals("x")) {
            raceTrack.toggleGridLines();
        }else
        if(e.getText().equals("m")) {
            raceTrack.toggleMinimap();
        }else
        if(e.getText().equals("g")) {
            raceTrack.toggleGoalLines();
        }else
        if(e.getCode() == KeyCode.SHIFT) {
            shiftDown = true;
        }
        camera.keyPressed(e);
    }
    
    public void keyReleased(KeyEvent e) {
        for(Car car : raceTrack.getCars()) {
            car.keyReleased(e);
        }
        if(e.getCode() == KeyCode.SHIFT) {
            shiftDown = false;
        }
        camera.keyReleased(e);
    }
    
    public void mouseScroll(boolean isScrollUp) {
        scrollIndex += isScrollUp?1:-1;
        if(scrollIndex>maxScrollIndex) {
            scrollIndex = 0;
        }else if(scrollIndex<0) {
            scrollIndex = maxScrollIndex;
        }
        switch (scrollIndex) {
        case 0:
            System.out.println("selected Wall");
            break;
        case 1:
            System.out.println("selected Spawn");
            break;
        case 2:
            System.out.println("selected Goal");
            break;
        default:
            break;
        }
    }
}
