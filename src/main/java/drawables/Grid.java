package drawables;

import java.util.ArrayList;

import NeuralNetworkGroup.NeuralNetworkArtifact.Constants;
import NeuralNetworkGroup.NeuralNetworkArtifact.GUIController;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import vectors.Vector2;

public class Grid extends DrawableObject{
    
    private GridCell[][] gridCells;
    private int rows,columns;
    private boolean showGridLines = false;
    private ArrayList<GridCell> wallGridCells = new ArrayList<GridCell>();
    private ArrayList<GridCell> spawnGridCells = new ArrayList<GridCell>();
    private GridCell hoverGridCell;

    public Grid(Color baseColor, Vector2 centerPoint) {
        super(baseColor, centerPoint);
        initGrid((int)(40*1.5),(int)(80*1.5),GUIController.getCanvasWidth()*1.5,GUIController.getCanvasHeight()*1.5);
    }
    
    public void initGrid(int rows, int columns, double width, double height) {
        GridCell.initSize(width, columns);
        double gridCellSize = GridCell.getSize();
        gridCells =  new GridCell[rows][columns];
        for (int i = 0;i<rows;i++) {
            for (int j = 0;j<columns;j++) {
                Vector2 gridCellCenterPoint = new Vector2(centerPoint.getX()-width/2+gridCellSize/2+gridCellSize*j, centerPoint.getY()-height/2+gridCellSize/2+gridCellSize*i);
                gridCells[i][j] = new GridCell(Constants.GROUND_COLOR, gridCellCenterPoint,i,j);
            }
        }
        this.rows = rows;
        this.columns = columns;
    }
    
    public int getRows(){
        return rows;
    }
    public int getColumns(){
        return columns;
    }
    // returns GridCell with specified row/column
    // return null if row/column isn't valid row/column in the Grid
    public GridCell tryGetGridCell(int row, int column) {
        if(row > rows-1 || row < 0 || column > columns-1 || column < 0) {
            return null;
        }
        return gridCells[row][column];
    }
    
    public GridCell[][] getGridCells() {
        return gridCells;
    }
    
    public ArrayList<GridCell> getWallGridCells() {
        return wallGridCells;
    }
    
    public ArrayList<GridCell> getSpawnGridCells() {
        return spawnGridCells;
    }
    
    public GridCell getHoverGridCell() {
        return hoverGridCell;
    }
    
    public void toggleShowGridLines() {
        showGridLines = !showGridLines;
    }

    @Override
    public void update(double secondsSinceLastFrame) {
        
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (int i = 0;i<gridCells.length;i++) {
            for (int j = 0;j<gridCells[i].length;j++) {
                gridCells[i][j].draw(gc);
                if(showGridLines) {
                    gridCells[i][j] .drawGridCellOutlineLine(gc);
                }
            }
        }
        if(hoverGridCell!=null) {
            hoverGridCell.drawHover(gc);
        }
    }

    @Override
    public void repositionGeometryOnResize() {
        
        
    }
    
    public void tryClickBoardCell(final MouseEvent e, final byte scrollIndex, final boolean shiftDown) {
        if(hoverGridCell==null) {
            return;
        }
        if(e.getButton() == MouseButton.PRIMARY) {
            switch (scrollIndex) {
            case 0:
                if(!wallGridCells.contains(hoverGridCell)) {
                    wallGridCells.add(hoverGridCell);
                    hoverGridCell.setWall(true);
                    
                }
                if(shiftDown) {
                        int row = hoverGridCell.getRow();
                        int column = hoverGridCell.getColumn();
                        if(row+1 <= rows) {
                            wallGridCells.add(gridCells[row+1][column]);
                            gridCells[row+1][column].setWall(true);
                        }
                        if(row-1 >= 0) {
                            wallGridCells.add(gridCells[row-1][column]);
                            gridCells[row-1][column].setWall(true);
                        }
                        if(column+1 <= columns) {
                            wallGridCells.add(gridCells[row][column+1]);
                            gridCells[row][column+1].setWall(true);
                        }
                        if(column-1 >= 0) {
                            wallGridCells.add(gridCells[row][column-1]);
                            gridCells[row][column-1].setWall(true);
                        }
                }
                break;
            case 1:
                if(!spawnGridCells.contains(hoverGridCell)) {
                    spawnGridCells.add(hoverGridCell);
                    hoverGridCell.setSpawn(true);
                }
                break;
            case 2:
                // !!! leave empty !!!
                break;
            default:
                break;
            }
            
        }else if(e.getButton() == MouseButton.SECONDARY){
            if(wallGridCells.contains(hoverGridCell)) {
                wallGridCells.remove(hoverGridCell);
                hoverGridCell.setWall(false);
                
            }
            if(shiftDown) {
                    int row = hoverGridCell.getRow();
                    int column = hoverGridCell.getColumn();
                    if(row+1 <= rows) {
                        wallGridCells.remove(gridCells[row+1][column]);
                        gridCells[row+1][column].setWall(false);
                    }
                    if(row-1 >= 0) {
                        wallGridCells.remove(gridCells[row-1][column]);
                        gridCells[row-1][column].setWall(false);
                    }
                    if(column+1 <= columns) {
                        wallGridCells.remove(gridCells[row][column+1]);
                        gridCells[row][column+1].setWall(false);
                    }
                    if(column-1 >= 0) {
                        wallGridCells.remove(gridCells[row][column-1]);
                        gridCells[row][column-1].setWall(false);
                    }
            }
            if(spawnGridCells.contains(hoverGridCell)) {
                spawnGridCells.remove(hoverGridCell);
                hoverGridCell.setSpawn(false);
            }
        }else if(e.getButton() == MouseButton.MIDDLE) {
            
        }
    }
    
    public void trySetHoverGridCell(Point2D mousePosition) {
        hoverGridCell = null;
        for (int i = 0;i<gridCells.length;i++) {
            for (int j = 0;j<gridCells[i].length;j++) {
                if(gridCells[i][j].containsPoint(mousePosition.getX(),mousePosition.getY())) {
                    hoverGridCell = gridCells[i][j];
                }
            }
        }
    }

}
