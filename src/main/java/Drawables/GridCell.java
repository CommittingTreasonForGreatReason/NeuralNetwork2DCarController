package drawables;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vectors.Vector2;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;

public class GridCell extends DrawableObject{
    
    
    private int row,column;
    private static double size;
    private boolean isWall,isSpawn;
    
    public static void initSize(double gridSize, int gridResolution) {
        size = Math.round(gridSize/gridResolution);
    }
    
    public boolean containsPoint(double x, double y) {
        Rectangle rectangle = new Rectangle(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        return rectangle.contains(new javafx.geometry.Point2D(x, y));
    }
    
    public boolean intersects(Rectangle rect) {
        Rectangle rectangle = new Rectangle(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        return rectangle.intersects(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
    }
    
    public GridCell(Color baseColor, Vector2 centerPoint, int row, int column) {
        super(baseColor, centerPoint);
        this.row = row;
        this.column = column;
    }
    
    public int getColumn() {
        return column;
    }
    public int getRow() {
        return row;
    }
    
    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }
    
    public void setSpawn(boolean isSpawn) {
        this.isSpawn = isSpawn;
    }
    
    public boolean isWall() {
        return isWall;
    }
    
    public boolean isSpawn() {
        return isSpawn;
    }
    
    public static double getSize() {
        return size;
    }

    @Override
    public void update(double secondsSinceLastFrame) {
          
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(isSpawn) {
            gc.setFill(Constants.SPAWN_COLOR1);
            gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size/2, size/2);
            gc.fillRect(centerPoint.getX(), centerPoint.getY(), size/2, size/2);
            gc.setFill(Constants.SPAWN_COLOR2);
            gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY(), size/2, size/2);
            gc.fillRect(centerPoint.getX(), centerPoint.getY()-size/2, size/2, size/2);
        }else {
           if(isWall) {
               gc.setFill(Constants.WALL_COLOR);
           } else {
               gc.setFill(baseColor);
           } 
           gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        }
    }
    
    public void drawGridCellOutlineLine(GraphicsContext gc) {
        gc.setStroke(Color.BLACK);
        gc.strokeRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
    }
    
    public void drawHover(GraphicsContext gc) {
        gc.setFill(Constants.HOVER_COLOR);
        gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
    }

    @Override
    public void repositionGeometryOnResize() {
        
    }
    
    public ArrayList<Line2D> getTrackLinesThisGridCell(GridCell[] neighborGridCells){
        ArrayList<Line2D> trackLinesThisGridCell =  new ArrayList<Line2D>();
        if(isWall) {
           return  trackLinesThisGridCell;
        }
//      [a][k][b]
//      [n][g][l]
//      [d][m][c]
        
        double centerX = centerPoint.getX();
        double centerY = centerPoint.getY();
        Point2D g = new Point2D.Double(centerX, centerY);
        Point2D a = new Point2D.Double(centerX-size/2, centerY-size/2);
        Point2D b = new Point2D.Double(centerX+size/2, centerY-size/2);
        Point2D c = new Point2D.Double(centerX+size/2, centerY+size/2);
        Point2D d = new Point2D.Double(centerX-size/2, centerY+size/2);
        Point2D k = new Point2D.Double(centerX, centerY-size/2);
        Point2D l = new Point2D.Double(centerX+size/2, centerY);
        Point2D m = new Point2D.Double(centerX, centerY+size/2);
        Point2D n = new Point2D.Double(centerX-size/2, centerY);
        
        int key0 = neighborGridCells[0] == null?1:(neighborGridCells[0].isWall?1:0);
        int key1 = neighborGridCells[1] == null?1:(neighborGridCells[1].isWall?1:0);
        int key2 = neighborGridCells[2] == null?1:(neighborGridCells[2].isWall?1:0);
        int key3 = neighborGridCells[3] == null?1:(neighborGridCells[3].isWall?1:0);
        int key = key0 + key1*2 + key2*4 + key3*8;
        
        switch (key) {
        case 0:
            break;
        case 1:
            trackLinesThisGridCell.add(new Line2D.Double(a,b));
            break;
        case 2:
            trackLinesThisGridCell.add(new Line2D.Double(b,c));
            break;
        case 3:
            trackLinesThisGridCell.add(new Line2D.Double(a,c));
            break;
        case 4:
            trackLinesThisGridCell.add(new Line2D.Double(d,c));
            break;
        case 5:
            trackLinesThisGridCell.add(new Line2D.Double(a,b));
            trackLinesThisGridCell.add(new Line2D.Double(d,c));
            break;
        case 6:
            trackLinesThisGridCell.add(new Line2D.Double(b,d));
            break;
        case 7:
            trackLinesThisGridCell.add(new Line2D.Double(a,g));
            trackLinesThisGridCell.add(new Line2D.Double(d,g));
            break;
        case 8:
            trackLinesThisGridCell.add(new Line2D.Double(a,d));
            break;
        case 9:
            trackLinesThisGridCell.add(new Line2D.Double(b,d));
            break;
        case 10:
            trackLinesThisGridCell.add(new Line2D.Double(a,d));
            trackLinesThisGridCell.add(new Line2D.Double(b,c));
            break;
        case 11:
            trackLinesThisGridCell.add(new Line2D.Double(d,g));
            trackLinesThisGridCell.add(new Line2D.Double(c,g));
            break;
        case 12:
            trackLinesThisGridCell.add(new Line2D.Double(a,c));
            break;
        case 13:
            trackLinesThisGridCell.add(new Line2D.Double(b,g));
            trackLinesThisGridCell.add(new Line2D.Double(c,g));
            break;
        case 14:
            trackLinesThisGridCell.add(new Line2D.Double(a,g));
            trackLinesThisGridCell.add(new Line2D.Double(b,g));
            break;
        case 15:
            break;
        default:
            break;
        }
        return trackLinesThisGridCell;
    }
    
    @Override
    public String toString() {
        return "GridCell:" + super.toString() + "\n(row/column)=(" + row + "/" + column + ") | " + (isWall?"is Wall":(isSpawn?"is Spawn":"is Track"));
    }

}
