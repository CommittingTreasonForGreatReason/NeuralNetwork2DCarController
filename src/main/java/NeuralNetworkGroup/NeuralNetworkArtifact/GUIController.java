package NeuralNetworkGroup.NeuralNetworkArtifact;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

public class GUIController {
	public static final double initial_width = Screen.getPrimary().getBounds().getWidth() * 0.9;
    public static final double initial_height = Screen.getPrimary().getBounds().getHeight() * 0.8;
	
	@FXML
	AnchorPane anchorPane;
	static ResizeableCanvas resizeableCanvas;
	static Renderer renderer;

    @FXML 
    private void initialize() {
    	resizeableCanvas = new ResizeableCanvas(initial_width,initial_height);
    	System.out.println("initialized ResizeableCanvas:\nwidth=" + resizeableCanvas.getWidth() + "\nheight=" + resizeableCanvas.getHeight());
    	anchorPane.getChildren().add(resizeableCanvas);
    	
    	renderer = new Renderer(resizeableCanvas);
    	
    	final GameLoopTimer timer = new GameLoopTimer() {
            @Override
            public void tick(float secondsSinceLastFrame) {
                renderer.update(secondsSinceLastFrame);
                renderer.prepare();
                renderer.render();
            }
        };
        
        
        // add listeners to capture and handle window resize events
        resizeableCanvas.widthProperty().addListener(e -> resizedCanvas());
        resizeableCanvas.heightProperty().addListener(e -> resizedCanvas());
        renderer.bind(anchorPane);
        System.out.println("bound renderer to anchorPane:");
        
        resizeableCanvas.setOnMouseClicked(this::mouseClicked);
        resizeableCanvas.setOnMouseMoved(this::mouseMoved);
        resizeableCanvas.setOnMouseDragged(this::mouseDragged);

        // start the main loop
        timer.start();
    }
    
    // event handle method for window resizing
    private void resizedCanvas() {
        // reposition geometry elements based on new window/canvas dimensions
        renderer.onResize();
    }
    
    public static double smallestSize() {
    	return resizeableCanvas.getWidth() > resizeableCanvas.getHeight()? resizeableCanvas.getHeight():resizeableCanvas.getWidth();
    }
    public static double getCanvasWidth() {
    	return resizeableCanvas.getWidth();
    }
    public static double getCanvasHeight() {
    	return resizeableCanvas.getHeight();
    }
    
    private void mouseClicked(final MouseEvent e) {
        renderer.mouseClicked(e);
    }
    private void mouseDragged(final MouseEvent e) {
        renderer.mouseDragged(e);
    }

    private void mouseMoved(final MouseEvent e) {
        renderer.mouseMoved(new Point2D(e.getX(),e.getY()));
    }
}
