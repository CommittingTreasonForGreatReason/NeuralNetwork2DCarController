package NeuralNetworkGroup.NeuralNetworkArtifact;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import drawables.Grid;
import drawables.GridCell;

public class MarchingSquareHelper {
    
    public static ArrayList<Line2D> getMarchingSquareLines(Grid grid){
        ArrayList<Line2D> marchingSquareLines = new ArrayList<Line2D>();
        GridCell gridCells[][] = grid.getGridCells();
        for (int i = 0;i<gridCells.length;i++) {
            for (int j = 0;j<gridCells[i].length;j++) {
                GridCell gridCell = gridCells[i][j];
                GridCell[] neighborGridCells = MarchingSquareHelper.getNeighborGridCells(gridCell,grid);
                
                    
                ArrayList<Line2D> trackLinesThisGridCell = MarchingSquareHelper.getTrackLinesThisGridCell(gridCell,neighborGridCells);
                for(Line2D line : trackLinesThisGridCell) {
                    marchingSquareLines.add(line);
                }
                
            }
        }
        return marchingSquareLines;
    }
    
    // format of Neighbors of Array [North,East,South,West]
    public static GridCell[] getNeighborGridCells(GridCell gridCell, Grid grid) {
        GridCell[] neighborGridCells = new GridCell[4];
        int row = gridCell.getRow();
        int column = gridCell.getColumn();
        neighborGridCells[0] = grid.tryGetGridCell(row-1, column);
        neighborGridCells[1] = grid.tryGetGridCell(row, column+1);
        neighborGridCells[2] = grid.tryGetGridCell(row+1, column);
        neighborGridCells[3] = grid.tryGetGridCell(row, column-1);
        return neighborGridCells;
    }
    
    public static ArrayList<Line2D> getTrackLinesThisGridCell(GridCell thisGridCell, GridCell[] neighborGridCells){
        ArrayList<Line2D> trackLinesThisGridCell =  new ArrayList<Line2D>();
        if(thisGridCell.isWall()) {
           return  trackLinesThisGridCell;
        }
//      [a][k][b]
//      [n][g][l]
//      [d][m][c]
        double size = GridCell.getSize();
        double centerX = thisGridCell.centerPoint.getX();
        double centerY = thisGridCell.centerPoint.getY();
        Point2D g = new Point2D.Double(centerX, centerY);
        
        Point2D a = new Point2D.Double(centerX-size/2, centerY-size/2);
        Point2D b = new Point2D.Double(centerX+size/2, centerY-size/2);
        Point2D c = new Point2D.Double(centerX+size/2, centerY+size/2);
        Point2D d = new Point2D.Double(centerX-size/2, centerY+size/2);
        
        Point2D k = new Point2D.Double(centerX, centerY-size/2);
        Point2D l = new Point2D.Double(centerX+size/2, centerY);
        Point2D m = new Point2D.Double(centerX, centerY+size/2);
        Point2D n = new Point2D.Double(centerX-size/2, centerY);
        
        int keyArray[] = new int[4];
        for(int i = 0;i<keyArray.length;i++) {
            keyArray[i] = neighborGridCells[i] == null?1:(neighborGridCells[i].isWall()?1:0);
        }
        int key = keyArray[0] + keyArray[1]*2 + keyArray[2]*4 + keyArray[3]*8;
        thisGridCell.initPolygon(key);
        
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
}
