package drawables;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import vectors.Vector2;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import NeuralNetworkGroup.NeuralNetworkArtifact.MarchingSquareHelper;
import graphics.Camera;

public class RaceTrack extends DrawableObject{
    
    private static RaceTrack raceTrack;

    private Grid grid;
    private Minimap minimap;
    public Camera camera;
    
    private ArrayList<Car> cars = new ArrayList<Car>();
    private ArrayList<Line2D> trackLines;
    private ArrayList<GoalLine> goalLines = new ArrayList<GoalLine>();
    private GoalLine editGoalLine = null;
    private boolean showGoalLines = true, showNeuralNetwork = true;
    
    private final int amountOfCars = 1;

    private RaceTrack(Color baseColor) {
        super(baseColor, new Vector2(GUIController.getCanvasWidth()/2, GUIController.getCanvasHeight()/2));
        System.out.println("initialized RaceTrack:");
        Car.setSizes();
        double width = GUIController.getCanvasWidth();
        double height = GUIController.getCanvasHeight();
        grid = new Grid(Color.GRAY, new Vector2(width/2, height/2));
        camera = Camera.getCameraInstance(getCenterX(),getCenterY());
        initTrackLines();
        initMinimap();
    }
    
    public static RaceTrack getRaceTrackInstance() {
        if (raceTrack == null) {
            raceTrack = new RaceTrack(Color.GRAY);
        }
        return raceTrack;
    }
    
    public Grid getGrid() {
        return grid;
    }
    public ArrayList<Car> getCars() {
        return cars;
    }
    public ArrayList<GoalLine> getGoalLines() {
        return goalLines;
    }
    
    public void toggleMinimap() {
        minimap.toggleIsShown();
    }
    
    public void toggleGridLines() {
        grid.toggleShowGridLines();
    }
    
    public void toggleGoalLines() {
        showGoalLines = !showGoalLines;
    }
    
    public void toggleNeuralNetwork() {
        showNeuralNetwork = !showNeuralNetwork;
    }
    
    public void saveNeuralNetwork() {
    	cars.get(0).saveNeuralNetwork("car0");
    }
    
    public void initMinimap() {
        double width = GUIController.getCanvasWidth();
        double height = GUIController.getCanvasHeight();
        minimap = new Minimap(Color.MAGENTA, new Vector2(getCenterX()+width/2-width*Minimap.scaleFactor/2 -20, getCenterY()-height/2+height*Minimap.scaleFactor/2 + 20), this);
    }
    
    public void spawnCars() {
        ArrayList<GridCell> spawnGridCells = grid.getSpawnGridCells();
        cars = new ArrayList<Car>();
        for(int i = 0;i<amountOfCars;i++) {
            Vector2 carPosition;
            if(spawnGridCells.size()>0) {
                int index = (int)(Math.random() * spawnGridCells.size());
                Vector2 spawnPoint = spawnGridCells.get(index).centerPoint;
                carPosition = new Vector2(spawnPoint.getX(),spawnPoint.getY());
            }else {
                carPosition = new Vector2(centerPoint.getX(), centerPoint.getY());
            }
            cars.add(new Car(carPosition));
        }
    }
    
    public void initTrackLines() {
        trackLines = MarchingSquareHelper.getMarchingSquareLines(grid);
    }
    
    public void initGoalLines(ArrayList<GoalLine> goalLines) {
        this.goalLines = goalLines;
    }
    
    
    
    @Override
    public void update(double secondsSinceLastFrame) {
        camera.move();
//        camera.follow((int)cars.get(0).getCenterX(), (int)cars.get(0).getCenterY());
        for(Car car : cars) {
            car.update(secondsSinceLastFrame);
            if(!car.isCrashed()) {
                car.updateCrashed(trackLines);
                if(goalLines.size()>0) {
                    car.updateGoalLineScore(goalLines);
                }
            }
        }
        minimap.update(secondsSinceLastFrame);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.translate(-camera.getX(), -camera.getY());
        grid.draw(gc);
        drawTrackLines(gc);
        drawGoalLines(gc);
        drawCars(gc);
        gc.translate(camera.getX(), camera.getY());
        if(minimap.isShown()) {
            minimap.draw(gc);
        }
        if(showNeuralNetwork) {
            cars.get(0).drawNeuralNetwork(gc);
        }
    }
    
    private void drawCars(GraphicsContext gc) {
        for(Car car : cars) {
            if(car.isCrashed()) {
                car.drawCrashedCross(gc);
            }
        }
        for(Car car : cars) {
            if(!car.isCrashed()) {
                car.draw(gc);
            }
        }
    }
    
    private void drawTrackLines(GraphicsContext gc) {
        gc.setLineWidth(3);
        gc.setStroke(Constants.TRACKLINE_COLOR);
        for(Line2D line : trackLines) {
            gc.strokeLine(line.getX1(),line.getY1(), line.getX2(), line.getY2());
        }
    }
    
    private void drawGoalLines(GraphicsContext gc) {
        gc.setLineWidth(3);
        gc.setFont(new Font("Arial",24));
        if(showGoalLines) {
            int lineIndex = 0;
            for(GoalLine goalLine : goalLines) {
                goalLine.draw(gc, lineIndex);
                lineIndex++;
            }
        }
        
        if(editGoalLine!=null) {
            drawEditGoalLine(gc);
        }
    }
    
    private void drawEditGoalLine(GraphicsContext gc) {
        editGoalLine.draw(gc, goalLines.size());
    }

    @Override
    public void repositionGeometryOnResize() {
        
    }
    
    private void tryEditGoalLine() {
        GridCell hoverGridCell = grid.getHoverGridCell();
        if(editGoalLine==null) {
                editGoalLine = new GoalLine(hoverGridCell.getRow(), hoverGridCell.getColumn(), hoverGridCell.getRow(), hoverGridCell.getColumn());
                editGoalLine.initLine(GridCell.getSize(),grid.getGridCells());
            }else {
                goalLines.add(editGoalLine);
                editGoalLine = null;
            }
    }
    
    private void tryDeleteGoalLine() {
        GridCell hoverGridCell = grid.getHoverGridCell();
        for(GoalLine goalLine : goalLines) {
            double hoverRow = hoverGridCell.getRow();
            double hoverColumn = hoverGridCell.getColumn();
            double lineRow1 = goalLine.getRow1();
            double lineColumn1 = goalLine.getColumn1();
            double lineRow2 = goalLine.getRow2();
            double lineColumn2 = goalLine.getColumn2();
            
            if((lineRow1 == hoverRow && lineColumn1 == hoverColumn) || (lineRow2 == hoverRow && lineColumn2 == hoverColumn)) {
                goalLines.remove(goalLine);
                return;
            }
        }
    }
    
    public void mouseClicked(final MouseEvent e, boolean isDrag, byte scrollIndex, boolean shiftDown) {
        if(scrollIndex == 2) {
        	if(isDrag) {
        		return;
        	}
            if(e.getButton() == MouseButton.PRIMARY) {
                tryEditGoalLine();
            }else if(e.getButton() == MouseButton.SECONDARY) {
                tryDeleteGoalLine();
                editGoalLine = null;
            }
        }else {
            grid.tryClickBoardCell(e,scrollIndex, shiftDown);
            initTrackLines();
        }
    }
    
    public void movedMouse(Point2D mousePosition) {
        grid.trySetHoverGridCell(mousePosition);
        updateEditGoalLineEndPoint();
    }
    
    private void updateEditGoalLineEndPoint() {
        GridCell hoverGridCell = grid.getHoverGridCell();
        if(editGoalLine!=null && hoverGridCell!=null) {
//            editGoalLine.setLine(editGoalLine.getX1(),editGoalLine.getY1(),hoverGridCell.getCenterX(),hoverGridCell.getCenterY());
            editGoalLine.setRowsAndColumns(editGoalLine.getRow1(), editGoalLine.getColumn1(), hoverGridCell.getRow(), hoverGridCell.getColumn());
            editGoalLine.initLine(GridCell.getSize(), grid.getGridCells());
        } 
    }

}
