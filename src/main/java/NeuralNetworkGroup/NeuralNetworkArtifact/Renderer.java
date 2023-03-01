package NeuralNetworkGroup.NeuralNetworkArtifact;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public final class Renderer {

    // canvas and graphic context
    private final ResizeableCanvas resizeableCanvas;

    private final GraphicsContext gc;

    // constructor
    Renderer(ResizeableCanvas resizeableCanvas) {
        // Set Debugging options for overlay
    	this.resizeableCanvas = resizeableCanvas;
    	gc = resizeableCanvas.getGraphicsContext2D();
    	System.out.println("initialized Renderer:");
    }

    // binds the anchor pane width/height to the canvas width/height
    public void bind(final AnchorPane pane) {
    	resizeableCanvas.widthProperty().bind(pane.widthProperty());
    	resizeableCanvas.heightProperty().bind(pane.heightProperty());
    }

    // the update method (called once per tick)
    public void update(final double secondsSinceLastFrame) {

    }

    // the render method (called once per tick)
    public void render() {
        gc.save();


        gc.restore();
    }

    // prepares next frame to be drawn
    public void prepare() {
        // clear entire canvas and fill it with background color
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
        gc.setStroke(Color.RED);
        gc.strokeLine(0, 0, resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
        gc.strokeLine(resizeableCanvas.getWidth(), 0, 0, resizeableCanvas.getHeight());
    }

    public void onResize() {
    	
        
    }
}
