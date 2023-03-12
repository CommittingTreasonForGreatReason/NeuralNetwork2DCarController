package NeuralNetworkGroup.NeuralNetworkArtifact;

import drawables.RaceTrack;
import fileAndData.MapFileManager;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class GUIController {
	public static final double initial_width = Screen.getPrimary().getBounds().getWidth() * 0.9;
    public static final double initial_height = Screen.getPrimary().getBounds().getHeight() * 0.8;
	
	@FXML
	AnchorPane anchorPane;
	@FXML
	Label mapManagerLabel;
	@FXML
	TextField mapManagerTextField;
	@FXML
    Pane mapManagerPane,mapManagerPaneBackGround;
	@FXML
	Button confirmButton,abortButton;
	
	private boolean isSaveMap;
	
	static ResizeableCanvas resizeableCanvas;
	static Renderer renderer;

    @FXML 
    private void initialize() {
    	resizeableCanvas = new ResizeableCanvas(initial_width,initial_height);
    	System.out.println("initialized ResizeableCanvas:\nwidth=" + resizeableCanvas.getWidth() + "\nheight=" + resizeableCanvas.getHeight());
    	anchorPane.getChildren().add(0,resizeableCanvas);
    	
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
        resizeableCanvas.setOnScroll(this::mouseScrolled);
        
        resizedCanvas();
        
        mapManagerPane.setVisible(false);
        // start the main loop
        timer.start();
        
    }
    
    // event handle method for window resizing
    private void resizedCanvas() {
        // reposition geometry elements based on new window/canvas dimensions
        renderer.onResize();
        
        mapManagerLabel.setLayoutX(resizeableCanvas.getWidth()/2-mapManagerLabel.getPrefWidth()/2);
        mapManagerLabel.setLayoutY(resizeableCanvas.getHeight()/4);
        
        mapManagerTextField.setLayoutX(resizeableCanvas.getWidth()/2-mapManagerTextField.getPrefWidth()/2);
        mapManagerTextField.setLayoutY(resizeableCanvas.getHeight()/2);
        
        mapManagerPane.setPrefSize(resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
        mapManagerPane.setLayoutX(0);
        mapManagerPane.setLayoutY(0);
        mapManagerPaneBackGround.setPrefSize(resizeableCanvas.getWidth(), resizeableCanvas.getHeight());
        mapManagerPaneBackGround.setLayoutX(0);
        mapManagerPaneBackGround.setLayoutY(0);
        
        double centerX = mapManagerPane.getPrefWidth()/2;
        double centerY = mapManagerPane.getPrefHeight()/2;
        double totalWidth = mapManagerPane.getPrefWidth();
        double totalHeight = mapManagerPane.getPrefHeight();
        
        confirmButton.setLayoutX(centerX - totalWidth/16 - confirmButton.getPrefWidth()/2);
        confirmButton.setLayoutY(centerY + totalHeight/8);
        abortButton.setLayoutX(centerX + totalWidth/16 - abortButton.getPrefWidth()/2);
        abortButton.setLayoutY(centerY + totalHeight/8);
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
    
    public void triggerMapDialog(boolean isSaveMap) {
        mapManagerPane.setVisible(true);
        this.isSaveMap = isSaveMap;
        mapManagerLabel.setText("do you want to " + (isSaveMap?"save":"load")  + " this map?");
    }
    @FXML
    private void confirmMap() {
        if(mapManagerTextField.getText() != "") {
            try {
                if(isSaveMap) {
                    MapFileManager.saveMap(RaceTrack.getRaceTrackInstance(), mapManagerTextField.getText());
                }else {
                    MapFileManager.loadMap(RaceTrack.getRaceTrackInstance(), mapManagerTextField.getText()); 
                }
                mapManagerPane.setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            System.err.println("Error type in a valid map name");
        }
    }
    @FXML
    private void abortMap() {
        mapManagerPane.setVisible(false);
    }
    
    private void mouseScrolled(ScrollEvent e) {
        renderer.mouseScrolled(e);
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
