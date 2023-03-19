package drawables;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
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

public class RaceTrack extends DrawableObject{
    
    private static RaceTrack raceTrack;
    
    private Grid grid;
    private final int amountOfCars = 1;
    private ArrayList<Car> cars;
    private ArrayList<Line2D> trackLines;
    private ArrayList<GoalLine> goalLines;
    private GoalLine editGoalLine = null;
    
    private byte scrollIndex = 0, maxScrollIndex=2;

    private RaceTrack(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
        System.out.println("initialized RaceTrack:");
        Car.setSizes();
        grid = new Grid(Color.GRAY, new Vector2(GUIController.getCanvasWidth()/2, GUIController.getCanvasHeight()/2));
    }
    
    public static RaceTrack getRaceTrackInstance() {
        if (raceTrack == null) {
            raceTrack = new RaceTrack(Color.GRAY, new Vector2(GUIController.getCanvasWidth()/2, GUIController.getCanvasHeight()/2));
        }
        return raceTrack;
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
        initTrackLines();
    }
    
    public void initTrackLines() {
        trackLines = MarchingSquareHelper.getMarchingSquareLines(grid);
    }
    
    public void initGoalLines(ArrayList<GoalLine> goalLines) {
        this.goalLines = goalLines;
    }
    
    public Grid getGrid() {
        return grid;
    }
    
    public ArrayList<GoalLine> getGoalLines() {
        return goalLines;
    }
    
    @Override
    public void update(double secondsSinceLastFrame) {
        for(Car car : cars) {
            car.update(secondsSinceLastFrame);
            if(!car.isCrashed()) {
                car.updateCrashed(trackLines);
                car.updateGoalLineScore(goalLines);
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        grid.draw(gc);
        drawTrackLines(gc);
        drawGoalLines(gc);
        drawCars(gc);
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
        int lineIndex = 0;
        for(GoalLine goalLine : goalLines) {
            goalLine.draw(gc, lineIndex);
            lineIndex++;
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
    
    private void tryEditGoalLine() {
        GridCell hoverGridCell = grid.getHoverGridCell();
        if(editGoalLine==null) {
                editGoalLine = new GoalLine(hoverGridCell.getRow(), hoverGridCell.getColumn(), hoverGridCell.getRow(), hoverGridCell.getColumn());
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
    
    public void mouseClicked(final MouseEvent e) {
        if(scrollIndex == 2) {
            if(e.getButton() == MouseButton.PRIMARY) {
                tryEditGoalLine();
            }else if(e.getButton() == MouseButton.SECONDARY) {
                tryDeleteGoalLine();
                editGoalLine = null;
            }
            
        }else {
            grid.tryClickBoardCell(e,scrollIndex);
            initTrackLines();
        }
    }
    
    public void mouseDragged(final MouseEvent e) {
        mouseMoved(new Point2D(e.getX(), e.getY()));
        mouseClicked(e);
    }
    
    public void mouseMoved(Point2D mousePosition){
        grid.trySetHoverGridCell(mousePosition);
        updateEditGoalLineEndPoint(mousePosition);
    }
    
    private void updateEditGoalLineEndPoint(Point2D mousePosition) {
        GridCell hoverGridCell = grid.getHoverGridCell();
        if(editGoalLine!=null) {
//            editGoalLine.setLine(editGoalLine.getX1(),editGoalLine.getY1(),hoverGridCell.getCenterX(),hoverGridCell.getCenterY());
            editGoalLine.setRowsAndColumns(editGoalLine.getRow1(), editGoalLine.getColumn1(), hoverGridCell.getRow(), hoverGridCell.getColumn());
        } 
    }
    
    public void keyPressed(KeyEvent e) {
        for(Car car : cars) {
            car.keyPressed(e);
        }
        if(e.getText().equals("x")) {
            grid.toggleShowGridLines();
        }
    }
    
    public void keyReleased(KeyEvent e) {
        for(Car car : cars) {
            car.keyReleased(e);
        }
    }

}
