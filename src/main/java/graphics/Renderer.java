package graphics;

import drawables.RaceTrack;
import fileAndData.MapFileManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public final class Renderer {

    // canvas and graphic context
    private final ResizeableCanvas resizeableCanvas;

    private final GraphicsContext gc;
    
    private RaceTrack raceTrack;

    // constructor
    public Renderer(ResizeableCanvas resizeableCanvas) {
        // Set Debugging options for overlay
    	this.resizeableCanvas = resizeableCanvas;
    	gc = resizeableCanvas.getGraphicsContext2D();
    	raceTrack = RaceTrack.getRaceTrackInstance();
    	System.out.println("initialized Renderer:");
    	MapFileManager.loadMap(RaceTrack.getRaceTrackInstance(), "schlong");
    }

    // binds the anchor pane width/height to the canvas width/height
    public void bind(final AnchorPane pane) {
    	resizeableCanvas.widthProperty().bind(pane.widthProperty());
    	resizeableCanvas.heightProperty().bind(pane.heightProperty());
    }

    // the update method (called once per tick)
    public void update(final double secondsSinceLastFrame) {
        raceTrack.update(secondsSinceLastFrame);
    }

    // the render method (called once per tick)
    public void render() {
        gc.save();

        raceTrack.draw(gc);

        gc.restore();
    }

    // prepares next frame to be drawn
    public void prepare() {
        // clear entire canvas and fill it with background color
        gc.setFill(Color.MAGENTA);
        gc.fillRect(0, 0, resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
//        gc.setStroke(Color.RED);
//        gc.strokeLine(0, 0, resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
//        gc.strokeLine(resizeableCanvas.getWidth(), 0, 0, resizeableCanvas.getHeight());
    }

    public void onResize() {
    	
        
    }
    
    public void mouseScrolled(ScrollEvent e) {
        raceTrack.mouseScroll(e.getDeltaY()>0);
    }
    
    public void mouseClicked(final MouseEvent e) {
        raceTrack.mouseClicked(e,false);
    }
    
    public void mouseDragged(final MouseEvent e) {
        raceTrack.mouseDragged(e);
    }
    
    public void mouseMoved(Point2D mousePosition){
        raceTrack.mouseMoved(mousePosition);
    }
}
