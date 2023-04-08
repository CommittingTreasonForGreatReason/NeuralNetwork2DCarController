package drawables;

import java.awt.geom.Line2D;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import vectors.Vector2;

public class GoalLine {
    private int row1,column1,row2,column2;
    private Line2D line;
    
    public GoalLine(int row1,int column1,int row2,int column2) {
        this.row1 = row1;
        this.row2 = row2;
        this.column1 = column1;
        this.column2 = column2;
    }
    
    public int getRow1() {
        return row1;
    }
    public int getColumn1() {
        return column1;
    }
    public int getRow2() {
        return row2;
    }
    public int getColumn2() {
        return column2;
    }
    
    public Line2D getLine() {
        return line;
    }
    
    public Vector2 getCenterPoint() {
        java.awt.Rectangle boundsRectangle = line.getBounds();
        return new Vector2(boundsRectangle.getCenterX(),boundsRectangle.getCenterY());
    }
    
    public void setRowsAndColumns(int row1,int column1,int row2,int column2) {
        this.row1 = row1;
        this.row2 = row2;
        this.column1 = column1;
        this.column2 = column2;
    }
    
    public void draw(GraphicsContext gc, int index) {
        gc.setLineWidth(3);
        gc.setFont(new Font("Arial",24));
        gc.setStroke(Constants.GOALLINE_COLOR);
        gc.strokeLine(line.getX1(),line.getY1(), line.getX2(), line.getY2());
        gc.setStroke(Color.BLACK);
        gc.strokeText(index+"", line.getBounds().getCenterX()-gc.getFont().getSize()/3, line.getBounds().getCenterY());
    }
    
    public void initLine(double gridCellSize, GridCell[][] gridCells) {
        line = new Line2D.Double(gridCells[row1][column1].getCenterX(), gridCells[row1][column1].getCenterY(),
                gridCells[row2][column2].getCenterX(), gridCells[row2][column2].getCenterY());
    }
}
