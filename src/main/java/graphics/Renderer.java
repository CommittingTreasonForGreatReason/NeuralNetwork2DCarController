package graphics;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import drawables.RaceTrack;
import fileAndData.MapFileManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

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
    	MapFileManager.loadMap(raceTrack, Constants.STARTMAP);
    }
    
    public RaceTrack getRaceTrack() {
        return raceTrack;
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
        gc.setFill(Constants.WALL_COLOR);
        gc.fillRect(0, 0, resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
//        gc.setStroke(Color.RED);
//        gc.strokeLine(0, 0, resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
//        gc.strokeLine(resizeableCanvas.getWidth(), 0, 0, resizeableCanvas.getHeight());
    }

    public void onResize() {
    	
        
    }
}
