package drawables;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import vectors.Vector2;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;

public class GridCell extends DrawableObject{
    
    private int row,column;
    private static int size;
    private boolean isWall,isSpawn;
    private double[] polygonX,polygonY;
    
    public static void initSize(double gridSize, int gridResolution) {
        size = (int) Math.round(gridSize/gridResolution);
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
    
    public boolean containsPoint(double x, double y) {
        Rectangle rectangle = new Rectangle(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        return rectangle.contains(new javafx.geometry.Point2D(x, y));
    }
    
    public boolean intersects(Rectangle rect) {
        Rectangle rectangle = new Rectangle(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
        return rectangle.intersects(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
    }
    
    public void initPolygon(int key) {
        ArrayList<Double> polygonXList = new ArrayList<Double>();
        ArrayList<Double> polygonYList = new ArrayList<Double>();
//      [a][ ][b]
//      [ ][g][ ]
//      [d][ ][c]
        double size = GridCell.getSize();
        double centerX = centerPoint.getX();
        double centerY = centerPoint.getY();
        Point2D g = new Point2D.Double(centerX, centerY);
        Point2D a = new Point2D.Double(centerX-size/2, centerY-size/2);
        Point2D b = new Point2D.Double(centerX+size/2, centerY-size/2);
        Point2D c = new Point2D.Double(centerX+size/2, centerY+size/2);
        Point2D d = new Point2D.Double(centerX-size/2, centerY+size/2);
        switch (key) {
        case 0:
            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            polygonXList.add(a.getX());
            polygonYList.add(a.getY());
            polygonXList.add(b.getX());
            polygonYList.add(b.getY());
            polygonXList.add(c.getX());
            polygonYList.add(c.getY());
            break;
        case 4:
            break;
        case 5:
            break;
        case 6:
            polygonXList.add(b.getX());
            polygonYList.add(b.getY());
            polygonXList.add(c.getX());
            polygonYList.add(c.getY());
            polygonXList.add(d.getX());
            polygonYList.add(d.getY());
            break;
        case 7:
            polygonXList.add(a.getX());
            polygonYList.add(a.getY());
            polygonXList.add(b.getX());
            polygonYList.add(b.getY());
            polygonXList.add(c.getX());
            polygonYList.add(c.getY());
            polygonXList.add(d.getX());
            polygonYList.add(d.getY());
            polygonXList.add(g.getX());
            polygonYList.add(g.getY());
            break;
        case 8:
            break;
        case 9:
            polygonXList.add(a.getX());
            polygonYList.add(a.getY());
            polygonXList.add(b.getX());
            polygonYList.add(b.getY());
            polygonXList.add(d.getX());
            polygonYList.add(d.getY());
            break;
        case 10:
            break;
        case 11:
            polygonXList.add(a.getX());
            polygonYList.add(a.getY());
            polygonXList.add(b.getX());
            polygonYList.add(b.getY());
            polygonXList.add(c.getX());
            polygonYList.add(c.getY());
            polygonXList.add(g.getX());
            polygonYList.add(g.getY());
            polygonXList.add(d.getX());
            polygonYList.add(d.getY());
            break;
        case 12:
            polygonXList.add(a.getX());
            polygonYList.add(a.getY());
            polygonXList.add(c.getX());
            polygonYList.add(c.getY());
            polygonXList.add(d.getX());
            polygonYList.add(d.getY());
            break;
        case 13:
            polygonXList.add(a.getX());
            polygonYList.add(a.getY());
            polygonXList.add(b.getX());
            polygonYList.add(b.getY());
            polygonXList.add(g.getX());
            polygonYList.add(g.getY());
            polygonXList.add(c.getX());
            polygonYList.add(c.getY());
            polygonXList.add(d.getX());
            polygonYList.add(d.getY());
            break;
        case 14:
            polygonXList.add(a.getX());
            polygonYList.add(a.getY());
            polygonXList.add(g.getX());
            polygonYList.add(g.getY());
            polygonXList.add(b.getX());
            polygonYList.add(b.getY());
            polygonXList.add(c.getX());
            polygonYList.add(c.getY());
            polygonXList.add(d.getX());
            polygonYList.add(d.getY());
            break;
        case 15:
            break;
        default:
            break;
        }
        polygonX = new double[polygonXList.size()];
        polygonY = new double[polygonYList.size()];
        
        for(int i = 0;i<polygonX.length;i++) {
            polygonX[i] = polygonXList.get(i);
            polygonY[i] = polygonYList.get(i);
        }
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
               gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
           } else {
               gc.setFill(baseColor);
               gc.fillRect(centerPoint.getX()-size/2, centerPoint.getY()-size/2, size, size);
               gc.setFill(Constants.WALL_COLOR);
               gc.fillPolygon(polygonX, polygonY, polygonX.length);
           } 
           
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
    
    @Override
    public String toString() {
        return "GridCell:" + super.toString() + "\n(row/column)=(" + row + "/" + column + ") | " + (isWall?"is Wall":(isSpawn?"is Spawn":"is Track"));
    }

}
